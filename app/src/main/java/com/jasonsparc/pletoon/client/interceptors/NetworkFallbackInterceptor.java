package com.jasonsparc.pletoon.client.interceptors;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * An application-level interceptor. The opposite of {@link CacheFallbackInterceptor}. Checks cache
 * first before doing network requests. This means that if an entry is cached, it will never be up
 * to date unless the outdated cache entry is removed.
 * <p>
 * Created by jasonsparc on 3/30/2016.
 */
public class NetworkFallbackInterceptor implements Interceptor {
	private static final NetworkFallbackInterceptor INSTANCE = new NetworkFallbackInterceptor();

	private NetworkFallbackInterceptor() {
	}

	public static NetworkFallbackInterceptor get() {
		return INSTANCE;
	}

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request request = chain.request();

		// Respects "no-cache" (or `CacheControl.FORCE_NETWORK`)
		if (!request.cacheControl().noCache()) {
			Request cached = request.newBuilder()
					.cacheControl(CacheControl.FORCE_CACHE)
					.build();
			Response response = chain.proceed(cached);
			if (response.code() != 504) {
				// The resource was cached! Show it.
				return response;
			}
		}

		// Not cached. Fallback to a network request.
		return chain.proceed(request);
	}
}
