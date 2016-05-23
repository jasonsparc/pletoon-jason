package com.jasonsparc.pletoon.app.setup;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.jasonsparc.pletoon.BR;
import com.jasonsparc.pletoon.R;
import com.jasonsparc.pletoon.databinding.DataBindingAction;
import com.jasonsparc.pletoon.databinding.DataBindingSetup;
import com.jasonsparc.pletoon.databinding.LayoutAllComicsBinding;
import com.jasonsparc.pletoon.model.Comic;

import java.util.Arrays;
import java.util.List;

import me.tatarka.bindingcollectionadapter.BindingViewPagerAdapter.PageTitles;
import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Created by jasonsparc on 5/22/2016.
 */
public class LayoutAllComicsSetup extends DataBindingSetup<LayoutAllComicsBinding> {
	public final ItemView sectionsItemView = ItemView.of(BR.setup, R.layout.section_day_all_comics);
	public final PageTitles<SectionDayAllComicsSetup> sectionsPageTitles = (position, item) -> item.day;

	@Override
	protected void onSetup(LayoutAllComicsBinding binding, View root) {
		// Does nothing yet.
	}

	public List<SectionDayAllComicsSetup> createSetupForEachDay(List<Comic> data, String[] days) {
		return SectionDayAllComicsSetup.createSetupForEachDay(data, Arrays.asList(days));
	}

	public DataBindingAction<ViewPager> bindTabsWithViewPager() {
		return viewPager -> {
			if (viewPager.getAdapter() == null) {
				viewPager = null;
			}
			binding().tabs.setupWithViewPager(viewPager);
		};
	}
}
