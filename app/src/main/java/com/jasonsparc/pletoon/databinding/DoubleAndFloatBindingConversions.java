package com.jasonsparc.pletoon.databinding;

import android.databinding.BindingConversion;

/**
 * Created by jasonsparc on 5/23/2016.
 */
public class DoubleAndFloatBindingConversions {

	@BindingConversion
	public static float toFloat(Double aDouble) {
		return aDouble.floatValue();
	}

	@BindingConversion
	public static double toDouble(Float aFloat) {
		return aFloat.doubleValue();
	}
}
