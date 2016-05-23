package com.jasonsparc.pletoon.log;

import android.util.Log;

/**
 * Created by jayson on 1/28/2016.
 */
public class LogUtils {

	public static void e(Throwable t) {
		e("Unhandled exception", t);
	}

	public static void e(String tag, Throwable t) {
		Log.e(tag, "Error thrown:", t);
	}

	public static void d(String msg) {
		d("LogUtils", msg);
	}

	public static void d(String tag, String msg) {
		Log.d(tag, msg);
	}
}
