package com.jasonsparc.pletoon.app.setup;

import android.support.annotation.ColorInt;
import android.view.View;
import android.widget.ImageView;

import com.jasonsparc.pletoon.databinding.DataBindingAction;
import com.jasonsparc.pletoon.view.ViewUtils;

/**
 * Created by jasonsparc on 5/23/2016.
 */
public class ItemEpisodeImageStaticSetup {

	public static DataBindingAction<ImageView> fillBackground(@ColorInt int color) {
		return v -> v.setBackgroundColor(color);
	}

	public static DataBindingAction<ImageView> hideEmptyStateView(int emptyStateViewId) {
		return v -> {
			View emptyStateView = ViewUtils.findNearestView(v, emptyStateViewId);
			if (emptyStateView != null)
				emptyStateView.setVisibility(View.INVISIBLE);
		};
	}
}
