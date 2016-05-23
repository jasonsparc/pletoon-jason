package com.jasonsparc.pletoon.core;

import android.content.SharedPreferences;

import com.bumptech.glide.load.engine.cache.DiskCache;
import com.google.gson.Gson;
import com.jasonsparc.pletoon.Q;
import com.jasonsparc.pletoon.client.PletoonAPI;
import com.jasonsparc.pletoon.client.db.PletoonDB;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by jasonsparc on 5/21/2016.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
	/*
	 * Note: All {@link Singleton @Singleton} annotations on methods are for reference purposes
	 * only, they don't actually do anything. Whether an injected dependency is a singleton or not
	 * depends on its Dagger provider.
	 */

	@Named(Q.EXT_FILES_DIR)
	File getExtFilesDir();

	@Named(Q.DEFAULT)
	@Singleton
	SharedPreferences defaultSharedPreferences();

	@Singleton
	Gson getGson();

	@Singleton
	OkHttpClient getClient();

	@Singleton
	@Named(Q.GLIDE)
	OkHttpClient getGlideClient();

	@Singleton
	Retrofit getRetrofit();

	@Singleton
	PletoonAPI getPletoonAPI();

	@Singleton
	PletoonDB getPletoonDB();

	DiskCache.Factory getGlideDiskCacheFactory();
}
