package com.jasonsparc.pletoon.rx;

import java.util.concurrent.Executor;

import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A convenience class to avoid verbose references to {@link Schedulers} or {@link
 * AndroidSchedulers}. Also, includes all of our extra utility {@link Scheduler}s.
 * <p>
 * Created by jasonsparc on 2/21/2016.
 */
public class Sched {

	/**
	 * A {@link Scheduler} which executes actions on the Android UI thread.
	 */
	public static Scheduler main() {
		return AndroidSchedulers.mainThread();
	}

	/**
	 * Creates and returns a {@link Scheduler} that executes work immediately on the current
	 * thread.
	 */
	public static Scheduler immediate() {
		return Schedulers.immediate();
	}

	/**
	 * Creates and returns a {@link Scheduler} that queues work on the current thread to be
	 * executed after the current work completes.
	 */
	public static Scheduler trampoline() {
		return Schedulers.trampoline();
	}

	/**
	 * Creates and returns a {@link Scheduler} that creates a new {@link Thread} for each unit of
	 * work.
	 * <p>
	 * Unhandled errors will be delivered to the scheduler Thread's {@link
	 * Thread.UncaughtExceptionHandler}.
	 */
	public static Scheduler newThread() {
		return Schedulers.newThread();
	}

	/**
	 * Creates and returns a {@link Scheduler} intended for computational work.
	 * <p>
	 * This can be used for event-loops, processing callbacks and other computational work.
	 * <p>
	 * Do not perform IO-bound work on this scheduler. Use {@link #io()} instead.
	 * <p>
	 * Unhandled errors will be delivered to the scheduler Thread's {@link
	 * Thread.UncaughtExceptionHandler}.
	 *
	 * @return a {@link Scheduler} meant for computation-bound work
	 */
	public static Scheduler computation() {
		return Schedulers.computation();
	}

	/**
	 * Creates and returns a {@link Scheduler} intended for IO-bound work.
	 * <p>
	 * The implementation is backed by an {@link Executor} thread-pool that will grow as needed.
	 * <p>
	 * This can be used for asynchronously performing blocking IO.
	 * <p>
	 * Do not perform computational work on this scheduler. Use {@link #computation()} instead.
	 * <p>
	 * Unhandled errors will be delivered to the scheduler Thread's {@link
	 * Thread.UncaughtExceptionHandler}.
	 *
	 * @return a {@link Scheduler} meant for IO-bound work
	 */
	public static Scheduler io() {
		return Schedulers.io();
	}

	/**
	 * Creates and returns a {@code TestScheduler}, which is useful for debugging. It allows you to
	 * test schedules of events by manually advancing the clock at whatever pace you choose.
	 *
	 * @return a {@code TestScheduler} meant for debugging
	 */
	public static Scheduler test() {
		return Schedulers.test();
	}

	/**
	 * Converts an {@link Executor} into a new Scheduler instance.
	 *
	 * @param executor the executor to wrap
	 * @return the new Scheduler wrapping the Executor
	 */
	public static Scheduler from(Executor executor) {
		return Schedulers.from(executor);
	}

	public static WorkerScheduler from(Worker worker) {
		return new WorkerScheduler(worker);
	}

	public static WorkerScheduler serial(Scheduler scheduler) {
		return from(scheduler.createWorker());
	}
}
