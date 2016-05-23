package com.jasonsparc.pletoon.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by jasonsparc on 5/21/2016.
 */
@AutoValue
public abstract class ComicWrapper implements Parcelable {

	public abstract Comic comic();

	public static TypeAdapter<ComicWrapper> typeAdapter(Gson gson) {
		return new AutoValue_ComicWrapper.GsonTypeAdapter(gson);
	}
}
