package com.jasonsparc.pletoon.model.adapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * Created by jasonsparc on 5/22/2016.
 */
public class NullSafeTypeAdapterFactory implements TypeAdapterFactory {
	final TypeAdapterFactory base;

	public static NullSafeTypeAdapterFactory wrap(TypeAdapterFactory base) {
		return base == null ? null : new NullSafeTypeAdapterFactory(base);
	}

	protected NullSafeTypeAdapterFactory(TypeAdapterFactory base) {
		if (base instanceof NullSafeTypeAdapterFactory)
			base = ((NullSafeTypeAdapterFactory) base).base;
		this.base = base;
	}

	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		return NullSafeTypeAdapter.wrap(base.create(gson, type));
	}
}
