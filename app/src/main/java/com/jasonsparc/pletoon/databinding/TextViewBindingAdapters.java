package com.jasonsparc.pletoon.databinding;

import android.content.res.ColorStateList;
import android.databinding.BindingAdapter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by jayson on 3/14/2016.
 */
public class TextViewBindingAdapters {

	@BindingAdapter("android:drawableTint")
	public static void setCompoundDrawableTintList(TextView textView, @Nullable ColorStateList tintList) {
		if (VERSION.SDK_INT >= VERSION_CODES.M) { // TODO Update target SDK in order to Marshmallow
			textView.setCompoundDrawableTintList(tintList);
		} else {
			Drawable[] drawables = textView.getCompoundDrawables();
			textView.setCompoundDrawables(
					wrapAndTint(drawables[0], tintList),
					wrapAndTint(drawables[1], tintList),
					wrapAndTint(drawables[2], tintList),
					wrapAndTint(drawables[3], tintList)
			);
		}
	}

	private static Drawable wrapAndTint(Drawable drawable, @Nullable ColorStateList tintList) {
		if (drawable != null) {
			drawable = DrawableCompat.wrap(drawable);
			DrawableCompat.setTintList(drawable, tintList);
		}
		return drawable;
	}

	private static Drawable wrapAndTint(Drawable drawable, @Nullable ColorStateList tintList, @Nullable PorterDuff.Mode tintMode) {
		if (drawable != null) {
			drawable = DrawableCompat.wrap(drawable);
			DrawableCompat.setTintList(drawable, tintList);
			DrawableCompat.setTintMode(drawable, tintMode);
		}
		return drawable;
	}

	private static Drawable wrapAndSetTintMode(Drawable drawable, @Nullable PorterDuff.Mode tintMode) {
		if (drawable != null) {
			drawable = DrawableCompat.wrap(drawable);
			DrawableCompat.setTintMode(drawable, tintMode);
		}
		return drawable;
	}

	@BindingAdapter("android:drawableTintMode")
	public static void setCompoundDrawableTintMode(TextView textView, @Nullable PorterDuff.Mode tintMode) {
		if (VERSION.SDK_INT >= VERSION_CODES.M) { // TODO Update target SDK in order to Marshmallow
			textView.setCompoundDrawableTintMode(tintMode);
		} else {
			Drawable[] drawables = textView.getCompoundDrawables();
			textView.setCompoundDrawables(
					wrapAndSetTintMode(drawables[0], tintMode),
					wrapAndSetTintMode(drawables[1], tintMode),
					wrapAndSetTintMode(drawables[2], tintMode),
					wrapAndSetTintMode(drawables[3], tintMode)
			);
		}
	}

	@BindingAdapter({"android:drawableTint", "android:drawableTintMode"})
	public static void setCompoundDrawableTintList(TextView textView, ColorStateList tintList, @Nullable PorterDuff.Mode tintMode) {
		if (VERSION.SDK_INT >= VERSION_CODES.M) { // TODO Update target SDK in order to Marshmallow
			textView.setCompoundDrawableTintList(tintList);
		} else {
			Drawable[] drawables = textView.getCompoundDrawables();
			textView.setCompoundDrawables(
					wrapAndTint(drawables[0], tintList, tintMode),
					wrapAndTint(drawables[1], tintList, tintMode),
					wrapAndTint(drawables[2], tintList, tintMode),
					wrapAndTint(drawables[3], tintList, tintMode)
			);
		}
	}

	// ======================================
	// Utilities
	// ======================================

	public static Locale getLocale(TextView view) {
		if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1) {
			return view.getTextLocale();
		} else {
			return view.getResources().getConfiguration().locale;
		}
	}
}
