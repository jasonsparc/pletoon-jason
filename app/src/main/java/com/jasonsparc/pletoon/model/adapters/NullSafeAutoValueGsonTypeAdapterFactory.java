package com.jasonsparc.pletoon.model.adapters;

import com.ryanharter.auto.value.gson.AutoValueGsonTypeAdapterFactory;

/**
 * Created by jasonsparc on 5/22/2016.
 */
public class NullSafeAutoValueGsonTypeAdapterFactory extends NullSafeTypeAdapterFactory {

	public NullSafeAutoValueGsonTypeAdapterFactory() {
		super(new AutoValueGsonTypeAdapterFactory());
	}
}
