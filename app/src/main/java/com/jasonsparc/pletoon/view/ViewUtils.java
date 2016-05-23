package com.jasonsparc.pletoon.view;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * Created by jayson on 3/21/2016.
 */
public class ViewUtils {

	public static View findNearestView(View view, int targetId) {
		View fromView = view.findViewById(targetId);
		if (fromView != null) {
			return fromView;
		}
		for (; ; ) {
			ViewParent parent = view.getParent();
			if (parent instanceof ViewGroup) {
				ViewGroup viewGroup = (ViewGroup) parent;
				int childCount = viewGroup.getChildCount();
				for (int i = 0; i < childCount; i++) {
					View child = viewGroup.getChildAt(i);
					if (view != child && (fromView = child.findViewById(targetId)) != null) {
						return fromView;
					}
				}
				view = viewGroup;
			} else
				return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T findMatchingParent(View view, Class<T> clazz) {
		ViewParent parent = view.getParent();
		if (parent == null) {
			return null;
		}
		if (clazz.isAssignableFrom(parent.getClass())) {
			return (T) parent;
		}
		if (parent instanceof View) {
			return findMatchingParent((View) parent, clazz);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T findMatchingContext(View view, Class<T> clazz) {
		Context context = view.getContext();
		for (; ; ) {
			if (clazz.isAssignableFrom(context.getClass())) {
				return (T) context;
			}
			if (context instanceof ContextWrapper) {
				context = ((ContextWrapper) context).getBaseContext();
			} else {
				return null;
			}
		}
	}
}
