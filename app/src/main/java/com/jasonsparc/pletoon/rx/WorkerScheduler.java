package com.jasonsparc.pletoon.rx;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action0;
import rx.subscriptions.CompositeSubscription;
import rx.subscriptions.Subscriptions;

/**
 * A scheduler that delegates all work to a {@link Worker} so that all work is guaranteed to be
 * sequential.
 * <p>
 * Created by jasonsparc on 2/21/2016.
 */
public class WorkerScheduler extends Scheduler implements Subscription {
	private final Worker base;

	public WorkerScheduler(Worker base) {
		this.base = base;
	}

	public Worker getBaseWorker() {
		return base;
	}

	@Override
	public void unsubscribe() {
		base.unsubscribe();
	}

	@Override
	public boolean isUnsubscribed() {
		return base.isUnsubscribed();
	}

	@Override
	public Worker createWorker() {
		return new SubWorker(base);
	}

	private static final class SubWorker extends Worker {
		private final Worker parent;
		private final CompositeSubscription compositeSubscription = new CompositeSubscription();

		public SubWorker(Worker parent) {
			this.parent = parent;
		}

		@Override
		public void unsubscribe() {
			compositeSubscription.unsubscribe();
		}

		@Override
		public boolean isUnsubscribed() {
			return compositeSubscription.isUnsubscribed();
		}

		@Override
		public Subscription schedule(Action0 action) {
			if (compositeSubscription.isUnsubscribed()) {
				return Subscriptions.unsubscribed();
			}
			Subscription subscription = parent.schedule(action);
			return Remover.wrap(compositeSubscription, subscription);
		}

		@Override
		public Subscription schedule(Action0 action, long delayTime, TimeUnit unit) {
			if (compositeSubscription.isUnsubscribed()) {
				return Subscriptions.unsubscribed();
			}
			Subscription subscription = parent.schedule(action, delayTime, unit);
			return Remover.wrap(compositeSubscription, subscription);
		}

		@Override
		public Subscription schedulePeriodically(Action0 action, long initialDelay, long period, TimeUnit unit) {
			if (compositeSubscription.isUnsubscribed()) {
				return Subscriptions.unsubscribed();
			}
			Subscription subscription = parent.schedulePeriodically(action, initialDelay, period, unit);
			return Remover.wrap(compositeSubscription, subscription);
		}

		@Override
		public long now() {
			return parent.now();
		}
	}

	private static final class Remover extends AtomicBoolean implements Subscription {
		private final Subscription s;
		private final CompositeSubscription parent;

		static Remover wrap(CompositeSubscription parent, Subscription s) {
			Remover remover = new Remover(s, parent);
			parent.add(remover);
			return remover;
		}

		private Remover(Subscription s, CompositeSubscription parent) {
			this.s = s;
			this.parent = parent;
		}

		@Override
		public void unsubscribe() {
			if (compareAndSet(false, true))
				parent.remove(s);
		}

		@Override
		public boolean isUnsubscribed() {
			return s.isUnsubscribed();
		}
	}
}
