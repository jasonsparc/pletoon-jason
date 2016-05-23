package com.jasonsparc.pletoon.databinding;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Created by jayson on 2/2/2016.
 */
public interface DataBindingAction<V extends View> {

	void call(V view) throws ClassCastException;

	class BindingAdapters {

		@SuppressWarnings("unchecked")
		@BindingAdapter("call")
		public static void call(View view, DataBindingAction action) throws ClassCastException {
			if (action != null)
				action.call(view);
		}
	}
}
