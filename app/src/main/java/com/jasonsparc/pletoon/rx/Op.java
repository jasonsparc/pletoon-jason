package com.jasonsparc.pletoon.rx;

import com.jasonsparc.pletoon.log.LogUtils;

import rx.Observable;
import rx.Observable.Transformer;
import rx.Scheduler;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by jayson on 1/28/2016.
 */
public class Op {

	public static <T> Transformer<T, T> sched(Scheduler subscribeOn, Scheduler observeOn) {
		return observable -> observable
				.subscribeOn(subscribeOn)
				.observeOn(observeOn)
				;
	}

	public static <T> Transformer<T, T> ignoreError() {
		return observable -> observable
				.onErrorResumeNext(throwable -> {
					LogUtils.e(throwable);
					return Observable.empty();
				})
				;
	}

	public static <T> Transformer<T, T> catchError(Action1<Throwable> onError) {
		return observable -> observable
				.onErrorResumeNext(throwable -> {
					LogUtils.e(throwable);
					onError.call(throwable);
					return Observable.empty();
				})
				;
	}

	public static <T> Transformer<T, T> handleError(Func1<Throwable, Boolean> onError) {
		return observable -> observable
				.onErrorResumeNext(throwable -> Boolean.TRUE.equals(onError.call(throwable)) ?
						Observable.empty() : Observable.error(throwable))
				;
	}
}
