package com.jasonsparc.pletoon.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by jasonsparc on 5/21/2016.
 */
@AutoValue
public abstract class Category implements HasID, Parcelable {

	public abstract String title();

	@Nullable public abstract ColorData backgroundColor();

	public static TypeAdapter<Category> typeAdapter(Gson gson) {
		return new AutoValue_Category.GsonTypeAdapter(gson);
	}
}
