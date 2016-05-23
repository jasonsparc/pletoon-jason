package com.jasonsparc.pletoon.util;

/**
 * Created by jayson on 3/18/2016.
 */
public class PixelUtils {

	/**
	 * Converts a float value representing a pixel dimension to its final value
	 * as an integer pixel value for use as a size.  A size
	 * conversion involves rounding the base value, and ensuring that a
	 * non-zero base value is at least one pixel in size.
	 */
	public static int floatToPixelSize(float value) {
		final int res = (int) (value + 0.5f);
		if (res != 0) return res;
		if (value == 0) return 0;
		if (value > 0) return 1;
		return -1;
	}
}
