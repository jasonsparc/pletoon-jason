package com.jasonsparc.pletoon.databinding;

import android.databinding.BindingAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;

import com.jasonsparc.pletoon.view.ViewUtils;

/**
 * Created by jasonsparc on 5/22/2016.
 */
public class HackBindingAdapters {

	/**
	 * See, http://stackoverflow.com/a/29946734/1906724
	 */
	@BindingAdapter("pagerInSwipeRefresh")
	public static void installFixPagerInSwipeRefresh(ViewPager pager, boolean dummy) {
		SwipeRefreshLayout refreshLayout = ViewUtils.findMatchingParent(pager, SwipeRefreshLayout.class);
		if (refreshLayout == null) {
			return;
		}

		ViewPager.OnPageChangeListener fix = new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				// Nothing.
			}

			@Override
			public void onPageSelected(int position) {
				// Nothing.
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				refreshLayout.setEnabled(state == ViewPager.SCROLL_STATE_IDLE);
			}
		};
		pager.addOnPageChangeListener(fix);
	}
}
