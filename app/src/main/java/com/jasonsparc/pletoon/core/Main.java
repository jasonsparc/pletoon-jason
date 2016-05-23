package com.jasonsparc.pletoon.core;

import android.os.Handler;
import android.os.Looper;

/**
 * Exposes {@link MainApplication}, other related stuffs as a singleton, and offers some utilities
 * that can be interpreted as a main component of the application.
 * <p>
 * Created by jasonsparc on 1/21/2016.
 */
public class Main {
	/**
	 * The main application context.
	 */
	public static final MainApplication context = MainApplication.instance;

	/**
	 * The main Dagger component dependency graph (i.e. an implementation of {@link AppComponent}).
	 */
	public static final AppComponent graph = MainApplication.graph;

	static {
		if (context == null)
			throw new AssertionError("Application is still not yet initialized. Main context is null.");
	}

	public static Handler handler() {
		return MainHandlerInternal.MAIN_THREAD_HANDLER;
	}

	private static class MainHandlerInternal {
		static final Handler MAIN_THREAD_HANDLER = new Handler(Looper.getMainLooper());
	}
}
