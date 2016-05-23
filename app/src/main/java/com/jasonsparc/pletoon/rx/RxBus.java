package com.jasonsparc.pletoon.rx;

import rx.Observable;
import rx.Observable.Transformer;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by jayson on 2/3/2016.
 */
public class RxBus<E> {
	final Subject<E, E> mEventSubject;
	final Observable<E> mObservable;

	public RxBus() {
		this(new SerializedSubject<>(PublishSubject.create()));
	}

	public RxBus(Transformer<? super E, ? extends E> observableTransformer) {
		this(new SerializedSubject<>(PublishSubject.create()), observableTransformer);
	}

	public RxBus(Subject<E, E> subject) {
		this(subject, observable -> observable);
	}

	public RxBus(Subject<E, E> subject, Transformer<? super E, ? extends E> observableTransformer) {
		mEventSubject = subject.toSerialized();
		mObservable = subject.compose(observableTransformer);
	}

	public void post(E event) {
		mEventSubject.onNext(event);
	}

	public Observable<E> observe() {
		return mObservable;
	}
}
