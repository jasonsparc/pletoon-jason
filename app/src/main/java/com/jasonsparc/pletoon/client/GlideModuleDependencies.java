package com.jasonsparc.pletoon.client;

import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.jasonsparc.pletoon.Q;
import com.jasonsparc.pletoon.client.interceptors.CacheFallbackInterceptor;
import com.jasonsparc.pletoon.client.interceptors.NetworkFallbackInterceptor;
import com.jasonsparc.pletoon.core.MainApplication;
import com.jasonsparc.pletoon.core.StorageUtils;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by jasonsparc on 1/26/2016.
 */
@Module
public class GlideModuleDependencies {

	/**
	 * 250 MB of cache.
	 */
	static final int DEFAULT_DISK_CACHE_SIZE = 250 * 1024 * 1024;

	@Provides
	DiskCache.Factory providesDiskCacheFactory(MainApplication context) {
		if (StorageUtils.isExternalStorageWritable()) {
			return new ExternalCacheDiskCacheFactory(context, DEFAULT_DISK_CACHE_SIZE);
		}
		return null;
	}

	@Provides
	@Singleton
	@Named(Q.GLIDE)
	OkHttpClient providesGlideOkHttpClient(OkHttpClient base) {
		OkHttpClient.Builder builder = base.newBuilder();  // Reuses the same connection pool

		// Uses NetworkFallbackInterceptor instead of CacheFallbackInterceptor.
		// Because images are usually meant to be cached forever.

		List<Interceptor> interceptors = builder.interceptors();
		NetworkFallbackInterceptor networkFallbackInterceptor = NetworkFallbackInterceptor.get();

		int indexOfCacheFallback = interceptors.indexOf(CacheFallbackInterceptor.get());
		if (indexOfCacheFallback >= 0) {
			interceptors.set(indexOfCacheFallback, networkFallbackInterceptor); // Replace!
		} else {
			interceptors.add(networkFallbackInterceptor); // Add normally.
		}

		/*
		 * We shouldn't need OkHttp's cache because Glide should cache the images for us. However,
		 * for some unknown reasons, Glide sometimes does not load the images from its own cache.
		 *
		 * TODO: Find out why!
		 */

		return builder.build();
	}
}
