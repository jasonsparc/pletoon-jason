package com.jasonsparc.pletoon.app.setup;

import android.support.annotation.Nullable;
import android.view.View;

import com.jasonsparc.pletoon.BR;
import com.jasonsparc.pletoon.R;
import com.jasonsparc.pletoon.databinding.DataBindingSetup;
import com.jasonsparc.pletoon.databinding.LayoutComicBinding;
import com.jasonsparc.pletoon.model.Episode;

import java.util.List;

import me.tatarka.bindingcollectionadapter.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Created by jasonsparc on 5/22/2016.
 */
public class LayoutComicSetup extends DataBindingSetup<LayoutComicBinding> {
	public final ItemView sectionsItemView = ItemView.of(BR.setup, R.layout.section_episode);
	public final BindingViewPagerAdapter.PageTitles<SectionEpisodeSetup> sectionsPageTitles = (position, item) -> String.valueOf(item.episode.order());

	public LayoutComicSetup() {
	}

	@Override
	protected void onSetup(LayoutComicBinding binding, View root) {
		// Does nothing.
	}

	public List<SectionEpisodeSetup> createSetupForEachEpisode(@Nullable List<Episode> episodes) {
		return SectionEpisodeSetup.createSetupForEachEpisode(episodes);
	}
}
