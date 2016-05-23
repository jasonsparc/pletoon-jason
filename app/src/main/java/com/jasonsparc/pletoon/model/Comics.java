package com.jasonsparc.pletoon.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

/**
 * Created by jasonsparc on 5/21/2016.
 */
@AutoValue
public abstract class Comics implements Parcelable {

	public abstract List<Comic> comics();

	public static Comics create(List<Comic> comics) {
		return new AutoValue_Comics(comics);
	}

	public static TypeAdapter<Comics> typeAdapter(Gson gson) {
		return new AutoValue_Comics.GsonTypeAdapter(gson);
	}
}
