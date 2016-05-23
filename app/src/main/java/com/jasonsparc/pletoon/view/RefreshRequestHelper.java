package com.jasonsparc.pletoon.view;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * A utility that provides the ability to easily manage reentrant refresh requests on {@link
 * SwipeRefreshLayout}. Requests are guaranteed to be thread-safe by posting them on the message
 * queue of the {@code SwipeRefreshLayout}.
 * <p>
 * Created by jasonsparc on 5/22/2016.
 */
public class RefreshRequestHelper {
	/**
	 * Delay to prevent a visual bug on the refresh indicator of the {@link SwipeRefreshLayout}.
	 */
	static final int DELAY_MILLIS = 50;

	final SwipeRefreshLayout refreshLayout;
	int refreshRequest; // Must be modified only in the UI thread.

	public RefreshRequestHelper(SwipeRefreshLayout refreshLayout) {
		this.refreshLayout = refreshLayout;
	}

	public void restoreRefreshing() {
		refreshLayout.postDelayed(() -> {
			boolean shouldBeRefreshing = refreshRequest > 0;
			if (shouldBeRefreshing != refreshLayout.isRefreshing())
				refreshLayout.setRefreshing(shouldBeRefreshing);
		}, DELAY_MILLIS);
	}

	public void requestRefreshing() {
		refreshLayout.postDelayed(() -> {
			refreshRequest++;
			if (!refreshLayout.isRefreshing())
				refreshLayout.setRefreshing(true);
		}, DELAY_MILLIS);
	}

	public void requestEndRefreshing() {
		refreshLayout.postDelayed(() -> {
			if (refreshRequest > 0) {
				refreshRequest--;
			}
			if (refreshRequest <= 0 && refreshLayout.isRefreshing())
				refreshLayout.setRefreshing(false);
		}, DELAY_MILLIS);
	}
}
