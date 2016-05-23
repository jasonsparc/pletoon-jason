package com.jasonsparc.pletoon.app.setup;

import android.support.annotation.Nullable;
import android.view.View;

import com.jasonsparc.pletoon.BR;
import com.jasonsparc.pletoon.R;
import com.jasonsparc.pletoon.databinding.DataBindingSetup;
import com.jasonsparc.pletoon.databinding.SectionEpisodeBinding;
import com.jasonsparc.pletoon.model.Episode;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter.ItemView;
import me.tatarka.bindingcollectionadapter.ItemViewSelector;

/**
 * Created by jasonsparc on 5/22/2016.
 */
public class SectionEpisodeSetup extends DataBindingSetup<SectionEpisodeBinding> {
	public final Episode episode;

	public final ItemViewSelector<Object> episodeImageItemView = new ItemViewSelector<Object>() {
		@Override
		public void select(ItemView itemView, int position, Object item) {
			if (position == 0) {
				itemView.set(BR.episode, R.layout.section_episode_header);
			} else {
				itemView.set(BR.image, R.layout.item_episode_image);
			}
		}

		@Override
		public int viewTypeCount() {
			return 2;
		}
	};

	public SectionEpisodeSetup(Episode episode) {
		this.episode = episode;
	}

	@Override
	protected void onSetup(SectionEpisodeBinding binding, View root) {
		// Does nothing yet.
	}

	public List<Object> provideItemsWithHeader() {
		ArrayList<Object> items = new ArrayList<>();
		items.add(episode); // Add header.

		List<Episode.Image> images = episode.images();
		if (images != null) {
			items.addAll(images);
		}
		return items;
	}

	// ======================================
	// DataBindingSetup Factories
	// ======================================

	public static List<SectionEpisodeSetup> createSetupForEachEpisode(@Nullable List<Episode> episodes) {
		if (episodes == null) {
			return new ArrayList<>(0);
		}
		ArrayList<SectionEpisodeSetup> setups = new ArrayList<>(episodes.size());
		for (Episode episode : episodes) {
			setups.add(new SectionEpisodeSetup(episode));
		}
		return setups;
	}
}
