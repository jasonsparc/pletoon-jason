package com.jasonsparc.pletoon.rxlifecycle;

import android.support.annotation.NonNull;

import com.trello.navi.component.support.NaviAppCompatDialogFragment;
import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.FragmentLifecycleProvider;
import com.trello.rxlifecycle.navi.NaviLifecycle;

import rx.Observable;
import rx.Observable.Transformer;

/**
 * Created by jayson on 1/29/2016.
 */
public class RxNaviAppCompatDialogFragment extends NaviAppCompatDialogFragment implements FragmentLifecycleProvider {
	private final FragmentLifecycleProvider lifecycleProvider = NaviLifecycle.createFragmentLifecycleProvider(this);

	@NonNull
	@Override
	public Observable<FragmentEvent> lifecycle() {
		return lifecycleProvider.lifecycle();
	}

	@NonNull
	@Override
	public <T> Transformer<T, T> bindUntilEvent(@NonNull FragmentEvent event) {
		return lifecycleProvider.bindUntilEvent(event);
	}

	@NonNull
	@Override
	public <T> Transformer<T, T> bindToLifecycle() {
		return lifecycleProvider.bindToLifecycle();
	}
}
