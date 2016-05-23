package com.jasonsparc.pletoon.databinding;

import android.databinding.BindingAdapter;
import android.support.v4.view.ViewPager;
import android.widget.AdapterView;

import java.util.List;

import me.tatarka.bindingcollectionadapter.BindingCollectionAdapters;
import me.tatarka.bindingcollectionadapter.BindingListViewAdapter;
import me.tatarka.bindingcollectionadapter.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter.ItemView;
import me.tatarka.bindingcollectionadapter.ItemViewArg;
import me.tatarka.bindingcollectionadapter.factories.BindingAdapterViewFactory;
import me.tatarka.bindingcollectionadapter.factories.BindingViewPagerAdapterFactory;

/**
 * Created by jasonsparc on 5/22/2016.
 */
public class BindingCollectionAdaptersWithAction {

	// AdapterView
	@SuppressWarnings("unchecked")
	@BindingAdapter(value = {"itemView", "items", "adapter", "dropDownItemView", "itemIds", "itemIsEnabled", "onAdapterBound"}, requireAll = false)
	public static <T> void setAdapter(AdapterView adapterView, ItemViewArg<T> arg, List<T> items, BindingAdapterViewFactory factory, ItemView dropDownItemView, BindingListViewAdapter.ItemIds<T> itemIds, BindingListViewAdapter.ItemIsEnabled<T> itemIsEnabled, DataBindingAction onAdapterBound) {
		BindingCollectionAdapters.setAdapter(adapterView, arg, items, factory, dropDownItemView, itemIds, itemIsEnabled);
		DataBindingAction.BindingAdapters.call(adapterView, onAdapterBound);
	}

	// ViewPager
	@SuppressWarnings("unchecked")
	@BindingAdapter(value = {"itemView", "items", "adapter", "pageTitles", "onAdapterBound"}, requireAll = false)
	public static <T> void setAdapter(ViewPager viewPager, ItemViewArg<T> arg, List<T> items, BindingViewPagerAdapterFactory factory, BindingViewPagerAdapter.PageTitles<T> pageTitles, DataBindingAction onAdapterBound) {
		BindingCollectionAdapters.setAdapter(viewPager, arg, items, factory, pageTitles);
		DataBindingAction.BindingAdapters.call(viewPager, onAdapterBound);
	}
}
