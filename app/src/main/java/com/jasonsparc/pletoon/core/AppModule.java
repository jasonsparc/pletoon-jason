package com.jasonsparc.pletoon.core;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.jasonsparc.pletoon.Q;
import com.jasonsparc.pletoon.client.NetworkModule;
import com.jasonsparc.pletoon.client.db.DatabaseModule;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jasonsparc on 5/21/2016.
 */
@Module(includes = {
		NetworkModule.class, DatabaseModule.class,
})
public class AppModule {
	final MainApplication application;

	AppModule(MainApplication application) {
		this.application = application;
	}

	@Provides
	MainApplication providesApplication() {
		return application;
	}

	@Provides
	@Singleton
	@Named(Q.EXT_FILES_DIR)
	File providesExtFilesDir(MainApplication context) {
		return StorageUtils.isExternalStorageWritable() ? context.getExternalFilesDir(null) : context.getFilesDir();
	}

	@Provides
	@Singleton
	@Named(Q.DEFAULT)
	SharedPreferences providesDefaultSharedPreferences(MainApplication application) {
		return PreferenceManager.getDefaultSharedPreferences(application);
	}
}
