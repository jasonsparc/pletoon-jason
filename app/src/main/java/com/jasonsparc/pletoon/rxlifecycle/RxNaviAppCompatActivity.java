package com.jasonsparc.pletoon.rxlifecycle;

import android.support.annotation.NonNull;

import com.trello.navi.component.support.NaviAppCompatActivity;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import com.trello.rxlifecycle.navi.NaviLifecycle;

import rx.Observable;
import rx.Observable.Transformer;

/**
 * Created by jayson on 1/29/2016.
 */
public class RxNaviAppCompatActivity extends NaviAppCompatActivity implements ActivityLifecycleProvider {
	private final ActivityLifecycleProvider lifecycleProvider = NaviLifecycle.createActivityLifecycleProvider(this);

	@NonNull
	@Override
	public Observable<ActivityEvent> lifecycle() {
		return lifecycleProvider.lifecycle();
	}

	@NonNull
	@Override
	public <T> Transformer<T, T> bindUntilEvent(@NonNull ActivityEvent event) {
		return lifecycleProvider.bindUntilEvent(event);
	}

	@NonNull
	@Override
	public <T> Transformer<T, T> bindToLifecycle() {
		return lifecycleProvider.bindToLifecycle();
	}
}
