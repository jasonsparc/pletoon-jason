package com.jasonsparc.pletoon.client;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.cache.DiskCache.Factory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.jasonsparc.pletoon.core.Main;

import java.io.InputStream;

/**
 * Created by jayson on 1/25/2016.
 */
public class PletoonGlideModule implements GlideModule {

	@Override
	public void applyOptions(Context context, GlideBuilder builder) {
		Factory diskCacheFactory = Main.graph.getGlideDiskCacheFactory();
		if (diskCacheFactory != null)
			builder.setDiskCache(diskCacheFactory);
	}

	@Override
	public void registerComponents(Context context, Glide glide) {
		glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(Main.graph.getGlideClient()));
	}
}
