package com.jasonsparc.pletoon.databinding;

import android.databinding.BindingAdapter;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;

import com.jasonsparc.pletoon.util.PixelUtils;

/**
 * Created by jayson on 3/25/2016.
 */
public class LayoutMarginBindingAdapters {

	@BindingAdapter(value = {
			"android:layout_margin",
			"android:layout_marginLeft",
			"android:layout_marginTop",
			"android:layout_marginRight",
			"android:layout_marginBottom",
			"android:layout_marginStart",
			"android:layout_marginEnd",
	}, requireAll = false)
	public static void setLayoutMargins(
			View v, Float marginAll,
			Float marginLeft, Float marginTop, Float marginRight, Float marginBottom,
			Float marginStart, Float marginEnd) {
		MarginLayoutParams lp = null;

		if (marginAll != null) {
			int margin = PixelUtils.floatToPixelSize(marginAll);
			lp = getLp(v);
			lp.leftMargin = margin;
			lp.topMargin = margin;
			lp.rightMargin = margin;
			lp.bottomMargin = margin;
			v.setLayoutParams(lp);
			return;
		}

		if (marginLeft != null) {
			lp = getLp(v);
			lp.leftMargin = PixelUtils.floatToPixelSize(marginLeft);
		}
		if (marginTop != null) {
			if (lp == null) lp = getLp(v);
			lp.topMargin = PixelUtils.floatToPixelSize(marginTop);
		}
		if (marginRight != null) {
			if (lp == null) lp = getLp(v);
			lp.rightMargin = PixelUtils.floatToPixelSize(marginRight);
		}
		if (marginBottom != null) {
			if (lp == null) lp = getLp(v);
			lp.bottomMargin = PixelUtils.floatToPixelSize(marginBottom);
		}

		if (marginStart != null) {
			if (lp == null) lp = getLp(v);
			MarginLayoutParamsCompat.setMarginStart(lp, PixelUtils.floatToPixelSize(marginStart));
		}
		if (marginEnd != null) {
			if (lp == null) lp = getLp(v);
			MarginLayoutParamsCompat.setMarginEnd(lp, PixelUtils.floatToPixelSize(marginEnd));
		}

		if (lp != null)
			v.setLayoutParams(lp);
	}

	private static MarginLayoutParams getLp(View v) {
		return (MarginLayoutParams) v.getLayoutParams();
	}
}
