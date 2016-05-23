package com.jasonsparc.pletoon.util;

import android.os.Bundle;

import com.trello.navi.Event;
import com.trello.navi.NaviComponent;

import icepick.Icepick;

/**
 * Created by jasonsparc on 5/22/2016.
 */
public class NaviIcepick {

	public static void installOnCreate(NaviComponent component, Bundle savedInstanceState) {
		Icepick.restoreInstanceState(component, savedInstanceState);
		component.addListener(Event.SAVE_INSTANCE_STATE, bundle -> Icepick.saveInstanceState(component, bundle));
	}
}
