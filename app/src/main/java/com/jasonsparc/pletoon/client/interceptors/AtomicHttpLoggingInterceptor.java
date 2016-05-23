package com.jasonsparc.pletoon.client.interceptors;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import okhttp3.logging.HttpLoggingInterceptor.Logger;

/**
 * An atomic equivalent of {@link HttpLoggingInterceptor}. Log messages will not be broken down
 * when used with multiple threads.
 * <p>
 * Created by jasonsparc on 4/3/2016.
 */
public final class AtomicHttpLoggingInterceptor implements Interceptor {
	private final HttpLoggingInterceptor delegate;
	private final AtomicLogger logger;

	public AtomicHttpLoggingInterceptor() {
		this(Logger.DEFAULT);
	}

	public AtomicHttpLoggingInterceptor(Logger logger) {
		this.delegate = new HttpLoggingInterceptor(this.logger = new AtomicLogger(logger));
	}

	public HttpLoggingInterceptor setLevel(Level level) {
		return delegate.setLevel(level);
	}

	public Level getLevel() {
		return delegate.getLevel();
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		try {
			return delegate.intercept(new RequestLoggingChain(logger, chain));
		} finally {
			logger.flush(); // Flushes logs about the response.
		}
	}

	private final class RequestLoggingChain implements Chain {
		private final AtomicLogger logger;
		private final Chain delegate;

		RequestLoggingChain(AtomicLogger logger, Chain delegate) {
			this.logger = logger;
			this.delegate = delegate;
		}

		@Override
		public Request request() {
			return delegate.request();
		}

		@Override
		public Response proceed(Request request) throws IOException {
			logger.flush(); // Flushes logs about the request.
			return delegate.proceed(request);
		}

		@Override
		public Connection connection() {
			return delegate.connection();
		}
	}

	private static final class AtomicLogger implements Logger {
		private final Logger delegate;

		private static final ThreadLocal<ArrayList<String>> tSink = new ThreadLocal<ArrayList<String>>() {
			@Override
			protected ArrayList<String> initialValue() {
				return new ArrayList<>();
			}
		};

		private static final Handler sHandler = new Handler(Looper.getMainLooper());

		AtomicLogger(Logger delegate) {
			this.delegate = delegate;
		}

		@Override
		public void log(String message) {
			tSink.get().add(message);
		}

		public void flush() {
			final ArrayList<String> messages = tSink.get();
			tSink.remove();

			sHandler.post(() -> {
				for (int i = 0, len = messages.size(); i < len; i++) {
					delegate.log(messages.get(i));
				}
			});
		}
	}
}
