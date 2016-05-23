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
public abstract class Episode implements Jet8Asset, HasID, Parcelable {

	@Nullable public abstract String title();

	@Nullable public abstract String status();

	@SerializedName("n_likes")
	public abstract int numOfLikes();

	@SerializedName("n_comments")
	public abstract int numOfComments();

	public abstract int order();

	@SerializedName("comic_id")
	public abstract String comicId();

	@SerializedName("created_at") @ParcelAdapter(DateParcelAdapter.class)
	public abstract Date createdAt();

	@SerializedName("updated_at") @ParcelAdapter(DateParcelAdapter.class)
	public abstract Date updatedAt();

	@SerializedName("publish_date") @ParcelAdapter(DateParcelAdapter.class)
	public abstract Date publishDate();

	public abstract boolean liked();

	@SerializedName("cover_picture_image")
	@Nullable public abstract String coverPictureImage();

	@SerializedName("images")
	@Nullable public abstract List<Image> images();

	public static TypeAdapter<Episode> typeAdapter(Gson gson) {
		return new AutoValue_Episode.GsonTypeAdapter(gson);
	}

	@AutoValue
	public static abstract class Image implements Jet8Asset, HasID, Parcelable {

		@SerializedName("aspect_ratio")
		public abstract double aspectRatio();

		public abstract int order();

		@SerializedName("episode_id")
		@Nullable public abstract String episodeId();

		@SerializedName("created_at") @ParcelAdapter(DateParcelAdapter.class)
		@Nullable public abstract Date createdAt();

		@SerializedName("updated_at") @ParcelAdapter(DateParcelAdapter.class)
		@Nullable public abstract Date updatedAt();

		public abstract String image();

		@SerializedName("master_id")
		@Nullable public abstract String masterId();

		public static TypeAdapter<Image> typeAdapter(Gson gson) {
			return new AutoValue_Episode_Image.GsonTypeAdapter(gson);
		}
	}
}
