package com.jasonsparc.pletoon.app.setup;

import android.support.annotation.Nullable;
import android.view.View;

import com.jasonsparc.pletoon.databinding.DataBindingSetup;
import com.jasonsparc.pletoon.databinding.ItemComicBinding;
import com.jasonsparc.pletoon.model.Comic;
import com.jasonsparc.pletoon.view.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jasonsparc on 5/22/2016.
 */
public class ItemComicSetup extends DataBindingSetup<ItemComicBinding> {
	public final Comic comic;
	OnClickComicListener onClickComicListener;

	public ItemComicSetup(Comic comic) {
		this.comic = comic;
	}

	@Override
	protected void onSetup(ItemComicBinding binding, View root) {
		onClickComicListener = ViewUtils.findMatchingContext(root, OnClickComicListener.class);
	}

	public View.OnClickListener provideOnClickListener() {
		return v -> {
			if (onClickComicListener != null)
				onClickComicListener.onClickComic(comic);
		};
	}

	// ======================================
	// Callbacks
	// ======================================

	public interface OnClickComicListener {

		void onClickComic(Comic comic);
	}

	// ======================================
	// DataBindingSetup Factories
	// ======================================

	public static List<ItemComicSetup> createSetupForEachComic(@Nullable List<Comic> comics) {
		if (comics == null) {
			return new ArrayList<>(0);
		}
		ArrayList<ItemComicSetup> setups = new ArrayList<>(comics.size());
		for (Comic comic : comics) {
			setups.add(new ItemComicSetup(comic));
		}
		return setups;
	}
}
