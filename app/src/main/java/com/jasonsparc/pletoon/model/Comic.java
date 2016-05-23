package com.jasonsparc.pletoon.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.jasonsparc.pletoon.model.adapters.DateParcelAdapter;
import com.ryanharter.auto.value.parcel.ParcelAdapter;

import java.util.Date;
import java.util.List;

/**
 * Created by jasonsparc on 5/21/2016.
 */
@AutoValue
public abstract class Comic implements Jet8Asset, HasID, Parcelable {

	public abstract String title();

	@Nullable public abstract String description();

	public abstract boolean highlighted();

	@Nullable public abstract String status();

	@SerializedName("user_id")
	@Nullable public abstract String userId();

	@SerializedName("n_rates")
	public abstract int numOfRates();

	@SerializedName("rate")
	public abstract float totalRating();

	@SerializedName("date_from") @ParcelAdapter(DateParcelAdapter.class)
	@Nullable public abstract Date dateFrom();

	@SerializedName("updated_at") @ParcelAdapter(DateParcelAdapter.class)
	@Nullable public abstract Date updatedAt();

	public abstract int views();

	@SerializedName("development_status")
	@Nullable public abstract String developmentStatus();

	public abstract boolean completed();

	@Nullable public abstract String theme();

	@SerializedName("background_color")
	@Nullable public abstract ColorData backgroundColor();

	@SerializedName("text_color")
	@Nullable public abstract ColorData textColor();

	@SerializedName("cell_background_color")
	@Nullable public abstract ColorData cellBackgroundColor();

	@SerializedName("cell_text_color")
	@Nullable public abstract ColorData cellTextColor();

	@Nullable public abstract String language();

	@SerializedName("cover_square_picture")
	@Nullable public abstract String coverSquarePicture();

	@SerializedName("artist_name")
	@Nullable public abstract String artistName();

	@SerializedName("principal_category")
	@Nullable public abstract Category principalCategory();

	@Nullable public abstract List<String> days();

	@Nullable public abstract String favourited();

	@SerializedName("cover_picture_image")
	@Nullable public abstract String coverPictureImage();

	@SerializedName("new_cover_picture_image")
	@Nullable public abstract String newCoverPictureimage();

	@SerializedName("square_image")
	@Nullable public abstract String squareImage();

	@SerializedName("cover_square_picture_image")
	@Nullable public abstract String coverSquarePictureImage();

	@SerializedName("highlighted_img_image")
	@Nullable public abstract String highlightedImgImage();

	@SerializedName("episodes_count")
	public abstract int episodesCount();

	@SerializedName("published_episodes")
	@Nullable public abstract List<Episode> publishedEpisodes();

	@SerializedName("categories")
	@Nullable public abstract List<Category> categories();

	public static TypeAdapter<Comic> typeAdapter(Gson gson) {
		return new AutoValue_Comic.GsonTypeAdapter(gson);
	}
}
