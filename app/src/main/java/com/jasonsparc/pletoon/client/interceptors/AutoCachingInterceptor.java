package com.jasonsparc.pletoon.client.interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * A network-level interceptor. Prevents OkHttp's caching engine from reading a `no-store`
 * Cache-Control.
 * <p>
 * This also ensures that a request is always cached, forever. This is achieved by removing
 * the Cache-Control header which is in turn interpreted by OkHttp's engine to always cache the
 * entry.
 * <p>
 * Used in conjunction with {@link CacheFallbackInterceptor} or {@link NetworkFallbackInterceptor}.
 * <p>
 * Created by jasonsparc on 11/23/2015.
 */
public class AutoCachingInterceptor implements Interceptor {
	private static final AutoCachingInterceptor INSTANCE = new AutoCachingInterceptor();

	private AutoCachingInterceptor() {
	}

	public static AutoCachingInterceptor get() {
		return INSTANCE;
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		return chain.proceed(chain.request()).newBuilder()
				.removeHeader("Cache-Control") // Ensures that `no-store` is never read
				.build();
	}
}
