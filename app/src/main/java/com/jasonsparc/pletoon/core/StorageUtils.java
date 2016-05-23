package com.jasonsparc.pletoon.core;

import android.os.Environment;

/**
 * Created by jasonsparc on 1/26/2016.
 */
public class StorageUtils {

	/**
	 * Checks if external storage is available for read and write
	 * <p>
	 * http://developer.android.com/guide/topics/data/data-storage.html#MediaAvail
	 */
	public static boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state);
	}

	/**
	 * Checks if external storage is available to at least read
	 * <p>
	 * http://developer.android.com/guide/topics/data/data-storage.html#MediaAvail
	 */
	public static boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(state) ||
				Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
	}
}
