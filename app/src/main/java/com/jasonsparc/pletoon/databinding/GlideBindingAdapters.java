package com.jasonsparc.pletoon.databinding;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

/**
 * Created by abigail on 2/26/2016.
 */
public class GlideBindingAdapters {

	@BindingAdapter(value = {"imageUrl", "placeholder", "onImageLoaded"}, requireAll = false)
	public static void setImageUrl(ImageView view, String imgUrl, Drawable placeholder, DataBindingAction onImageLoaded) {
		view.setImageDrawable(placeholder);

		DrawableRequestBuilder<String> request = Glide.with(view.getContext()).load(imgUrl).placeholder(placeholder);

		if (onImageLoaded == null) {
			request.into(view);
			return;
		}

		request.into(new ViewTarget<ImageView, GlideDrawable>(view) {
			@Override
			public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
				this.view.setImageDrawable(resource);
				DataBindingAction.BindingAdapters.call(view, onImageLoaded);
			}
		});
	}
}
