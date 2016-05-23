package com.jasonsparc.pletoon.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jasonsparc on 5/21/2016.
 */
public interface Jet8Asset extends HasID {

	@SerializedName("jet8_asset_id")
	@Nullable String jet8AssetId();
}
