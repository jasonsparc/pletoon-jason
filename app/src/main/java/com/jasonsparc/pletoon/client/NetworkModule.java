package com.jasonsparc.pletoon.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jasonsparc.pletoon.Q;
import com.jasonsparc.pletoon.client.cookies.DatabaseCookieJar;
import com.jasonsparc.pletoon.client.interceptors.InterceptorsModule;
import com.jasonsparc.pletoon.core.MainApplication;
import com.jasonsparc.pletoon.core.StorageUtils;
import com.jasonsparc.pletoon.model.adapters.NullSafeAutoValueGsonTypeAdapterFactory;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jasonsparc on 5/21/2016.
 */
@Module(includes = {
		InterceptorsModule.class, GlideModuleDependencies.class,
})
public class NetworkModule {

	@Provides
	Retrofit.Builder providesRetrofitBuilder(OkHttpClient client, Gson gson) {
		return new Retrofit.Builder()
				.baseUrl(PletoonAPI.BASE_URL)
				.client(client)
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create(gson));
	}

	/**
	 * 250 MB of cache.
	 */
	static final int DEFAULT_DISK_CACHE_SIZE = 250 * 1024 * 1024;
	static final String DEFAULT_DISK_CACHE_DIR = "okhttp";

	@Provides
	Cache providesOkHttpCache(MainApplication context) {
		File cacheDir = StorageUtils.isExternalStorageWritable() ? context.getExternalCacheDir() : context.getCacheDir();
		return new Cache(new File(cacheDir, DEFAULT_DISK_CACHE_DIR), DEFAULT_DISK_CACHE_SIZE);
	}

	@Provides
	CookieJar providesOkHttpCookieJar() {
		return new DatabaseCookieJar();
	}

	// ======================================
	// Singletons
	// ======================================

	@Provides
	@Singleton
	Gson providesGson() {
		return new GsonBuilder()
				.registerTypeAdapterFactory(new NullSafeAutoValueGsonTypeAdapterFactory())
				.setDateFormat("yyyy-MM-dd HH:mm:ss")
				.disableHtmlEscaping()
				.create();
	}

	@Provides
	@Singleton
	Retrofit providesRetrofit(Retrofit.Builder builder) {
		return builder.build();
	}

	@SuppressWarnings("Convert2streamapi")
	@Provides
	@Singleton
	OkHttpClient providesOkHttpClient(
			Cache cache, CookieJar cookieJar,
			@Named(Q.APPLICATION_INTERCEPTOR) Set<Collection<Interceptor>> applicationInterceptors,
			@Named(Q.NETWORK_INTERCEPTOR) Set<Collection<Interceptor>> networkInterceptors,
			@Named(Q.LOGGING_INTERCEPTOR) Interceptor loggingInterceptor
	) {
		OkHttpClient.Builder builder = new OkHttpClient.Builder()
				.cache(cache)
				.cookieJar(cookieJar);

		List<Interceptor> builderInterceptors = builder.interceptors();
		List<Interceptor> builderNetworkInterceptors = builder.networkInterceptors();

		for (Collection<Interceptor> interceptors : applicationInterceptors)
			builderInterceptors.addAll(interceptors);

		for (Collection<Interceptor> interceptors : networkInterceptors)
			builderNetworkInterceptors.addAll(interceptors);

		if (loggingInterceptor != null)
			builderInterceptors.add(loggingInterceptor);

		return builder.build();
	}

	@Provides
	@Singleton
	PletoonAPI providesPletoonAPI(Retrofit retrofit) {
		return retrofit.create(PletoonAPI.class);
	}
}
