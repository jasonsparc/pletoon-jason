package com.jasonsparc.pletoon.app.setup;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.jasonsparc.pletoon.BR;
import com.jasonsparc.pletoon.R;
import com.jasonsparc.pletoon.databinding.DataBindingSetup;
import com.jasonsparc.pletoon.databinding.SectionDayAllComicsBinding;
import com.jasonsparc.pletoon.model.Comic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import me.tatarka.bindingcollectionadapter.ItemView;

/**
 * Created by jasonsparc on 5/22/2016.
 */
public class SectionDayAllComicsSetup extends DataBindingSetup<SectionDayAllComicsBinding> {
	public final String day;
	public final List<Comic> comics;

	public final ItemView comicItemView = ItemView.of(BR.setup, R.layout.item_comic);

	public SectionDayAllComicsSetup(String day, @NonNull List<Comic> comics) {
		this.day = day;
		this.comics = new ArrayList<>(comics);
	}

	@Override
	protected void onSetup(SectionDayAllComicsBinding binding, View root) {
		// Does nothing yet.
	}

	public List<ItemComicSetup> createSetupForEachComic() {
		return ItemComicSetup.createSetupForEachComic(comics);
	}

	// ======================================
	// DataBindingSetup Factories
	// ======================================

	public static List<SectionDayAllComicsSetup> createSetupForEachDay(@Nullable List<Comic> data, String[] days) {
		return createSetupForEachDay(data, Arrays.asList(days));
	}

	public static List<SectionDayAllComicsSetup> createSetupForEachDay(@Nullable List<Comic> data, List<String> days) {
		return createSetupForEachDay(data, new LinkedHashSet<>(days));
	}

	public static List<SectionDayAllComicsSetup> createSetupForEachDay(@Nullable List<Comic> data, LinkedHashSet<String> days) {
		LinkedHashMap<String, List<Comic>> sections = new LinkedHashMap<>(days.size());

		for (String day : days) {
			sections.put(day, new ArrayList<>());
		}

		if (data != null) {
			for (Comic comic : data) {
				List<String> comicDays = comic.days();
				if (comicDays == null) {
					continue;
				}
				for (String day : comicDays) {
					List<Comic> section = sections.get(day);
					if (section != null) {
						section.add(comic);
					}
				}
			}
		}

		ArrayList<SectionDayAllComicsSetup> setups = new ArrayList<>(days.size());
		for (Map.Entry<String, List<Comic>> entry : sections.entrySet()) {
			setups.add(new SectionDayAllComicsSetup(entry.getKey(), entry.getValue()));
		}

		return setups;
	}
}
