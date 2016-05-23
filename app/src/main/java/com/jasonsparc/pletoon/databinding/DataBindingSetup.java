package com.jasonsparc.pletoon.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;

/**
 * Created by jayson on 2/4/2016.
 */
public abstract class DataBindingSetup<B extends ViewDataBinding> implements DataBindingAction<View> {
	B binding;

	public B binding() {
		return binding;
	}

	/**
	 * @param view A <code>View</code> in the bound layout.
	 * @throws IllegalArgumentException when view is not from an inflated binding layout.
	 */
	@Override
	public void call(View view) {
		binding = DataBindingUtil.findBinding(view);
		if (binding == null) {
			throw new IllegalArgumentException("View is not a binding layout");
		}
		reset();
	}

	protected abstract void onSetup(B binding, View root);

	public void reset() {
		if (binding != null)
			onSetup(binding, binding.getRoot());
	}
}
