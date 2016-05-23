package com.jasonsparc.pletoon.databinding;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.view.View;

/**
 * Created by jayson on 2/19/2016.
 */
public class VisibilityBindingAdapters {

	@BindingAdapter("visible")
	public static void setVisible(View view, Object object) {
		view.setVisibility(object != null ? View.VISIBLE : View.GONE);
	}

	@BindingAdapter("gone")
	public static void setGone(View view, Object object) {
		view.setVisibility(object != null ? View.GONE : View.VISIBLE);
	}

	@BindingAdapter("invisible")
	public static void setInvisible(View view, Object object) {
		view.setVisibility(object != null ? View.INVISIBLE : View.VISIBLE);
	}

	@BindingAdapter("visible")
	public static void setVisible(View view, boolean set) {
		view.setVisibility(set ? View.VISIBLE : View.GONE);
	}

	@BindingAdapter("gone")
	public static void setGone(View view, boolean set) {
		view.setVisibility(set ? View.GONE : View.VISIBLE);
	}

	@BindingAdapter("invisible")
	public static void setInvisible(View view, boolean set) {
		view.setVisibility(set ? View.INVISIBLE : View.VISIBLE);
	}

	@BindingAdapter("visible")
	public static void setVisible(View view, Boolean set) {
		view.setVisibility(Boolean.TRUE.equals(set) ? View.VISIBLE : View.GONE);
	}

	@BindingAdapter("gone")
	public static void setGone(View view, Boolean set) {
		view.setVisibility(Boolean.TRUE.equals(set) ? View.GONE : View.VISIBLE);
	}

	@BindingAdapter("invisible")
	public static void setInvisible(View view, Boolean set) {
		view.setVisibility(Boolean.TRUE.equals(set) ? View.INVISIBLE : View.VISIBLE);
	}

	@BindingAdapter("visible")
	public static void setVisible(View view, CharSequence text) {
		view.setVisibility(!TextUtils.isEmpty(text) ? View.VISIBLE : View.GONE);
	}

	@BindingAdapter("gone")
	public static void setGone(View view, CharSequence text) {
		view.setVisibility(!TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
	}

	@BindingAdapter("invisible")
	public static void setInvisible(View view, CharSequence text) {
		view.setVisibility(!TextUtils.isEmpty(text) ? View.INVISIBLE : View.VISIBLE);
	}
}
