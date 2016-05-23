package com.jasonsparc.pletoon.core;

import android.app.Application;

/**
 * Created by jasonsparc on 5/21/2016.
 */
public class MainApplication extends Application {
	static MainApplication instance;
	static AppComponent graph;

	@Override
	public void onCreate() {
		super.onCreate();
		graph = DaggerAppComponent.builder()
				.appModule(new AppModule(this))
				.build();
		instance = this;
		_initializer.install(this);
	}
}
