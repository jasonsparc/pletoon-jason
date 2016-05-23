package com.jasonsparc.pletoon.client.interceptors;

import android.util.Log;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * An application-level interceptor. Fallbacks to an existing cached entry whenever a request
 * fails.
 * <p>
 * Created by jasonsparc on 3/30/2016.
 */
public class CacheFallbackInterceptor implements Interceptor {
	private static final String TAG = "CacheFallback";
	private static final CacheFallbackInterceptor INSTANCE = new CacheFallbackInterceptor();

	private CacheFallbackInterceptor() {
	}

	public static CacheFallbackInterceptor get() {
		return INSTANCE;
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();
		IOException exception;
		try {
			return chain.proceed(request);
		} catch (IOException e) {
			exception = e; // TODO catch only UnknownHostException and SocketTimeoutException ???
		}

		// Respects "no-cache" (or `CacheControl.FORCE_NETWORK`)
		if (!request.cacheControl().noCache()) {
			Request cached = request.newBuilder()
					.cacheControl(CacheControl.FORCE_CACHE)
					.build();
			Response response = chain.proceed(cached);
			if (response.code() != 504) {
				Log.e(TAG, "Error!", exception);

				// The resource was cached! Show it.
				return response;
			}
		}

		// The resource was not cached or "no-cache" directive present

		throw exception; // throw original exception
	}
}
