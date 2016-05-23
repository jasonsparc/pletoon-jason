package com.jasonsparc.pletoon.databinding;

import android.databinding.BindingAdapter;
import android.support.percent.PercentLayoutHelper;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jasonsparc on 5/23/2016.
 */
public class PercentLayoutInfoBindingAdapters {

	@BindingAdapter(value = {
			"layout_heightPercent",
			"layout_widthPercent",
			"layout_aspectRatio",
			"layout_marginPercent",

			"layout_marginLeftPercent",
			"layout_marginTopPercent",
			"layout_marginRightPercent",
			"layout_marginBottomPercent",
			"layout_marginStartPercent",
			"layout_marginEndPercent",
	}, requireAll = false)
	public static void setAspectRatio(
			View v,

			Float heightPercent,
			Float widthPercent,
			Float aspectRatio,

			Float marginAllPercent,
			Float marginLeftPercent, Float marginTopPercent,
			Float marginRightPercent, Float marginBottomPercent,
			Float marginStartPercent, Float marginEndPercent) {

		ViewGroup.LayoutParams lp = v.getLayoutParams();
		PercentLayoutHelper.PercentLayoutInfo lpi = ((PercentLayoutHelper.PercentLayoutParams) lp).getPercentLayoutInfo();

		if (heightPercent != null) lpi.heightPercent = heightPercent;
		if (widthPercent != null) lpi.widthPercent = widthPercent;
		if (aspectRatio != null) lpi.aspectRatio = aspectRatio;

		if (marginAllPercent != null) {
			lpi.leftMarginPercent = marginLeftPercent;
			lpi.topMarginPercent = marginTopPercent;
			lpi.rightMarginPercent = marginRightPercent;
			lpi.bottomMarginPercent = marginBottomPercent;
			v.setLayoutParams(lp);
			return;
		}

		if (marginLeftPercent != null) lpi.leftMarginPercent = marginLeftPercent;
		if (marginTopPercent != null) lpi.topMarginPercent = marginTopPercent;
		if (marginRightPercent != null) lpi.rightMarginPercent = marginRightPercent;
		if (marginBottomPercent != null) lpi.bottomMarginPercent = marginBottomPercent;

		if (marginStartPercent != null) lpi.startMarginPercent = marginStartPercent;
		if (marginEndPercent != null) lpi.endMarginPercent = marginEndPercent;

		v.setLayoutParams(lp);
	}
}
