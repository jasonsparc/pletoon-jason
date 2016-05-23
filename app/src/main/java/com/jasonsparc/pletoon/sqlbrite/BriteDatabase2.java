package com.jasonsparc.pletoon.sqlbrite;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.BriteDatabase.ConflictAlgorithm;
import com.squareup.sqlbrite.BriteDatabase.Transaction;
import com.squareup.sqlbrite.QueryObservable;

import rx.Observable;
import rx.Subscription;
import rx.functions.Func2;
import rx.observables.SyncOnSubscribe;
import rx.subscriptions.Subscriptions;

/**
 * Wraps a {@link BriteDatabase} to provide Observable-based operations for inserts, updates,
 * deletes, and any operation that modifies a database.
 * <p>
 * Created by jasonsparc on 4/22/2016.
 */
public class BriteDatabase2 {
	final BriteDatabase base;

	public BriteDatabase2(BriteDatabase base) {
		this.base = base;
	}

	/**
	 * @see BriteDatabase#setLoggingEnabled(boolean)
	 */
	public void setLoggingEnabled(boolean enabled) {
		base.setLoggingEnabled(enabled);
	}

	/**
	 * @see BriteDatabase#close()
	 */
	public void close() {
		base.close();
	}

	public BriteDatabase base() {
		return base;
	}

	/**
	 * TODO Document
	 *
	 * @see BriteDatabase#newTransaction()
	 */
	@CheckResult
	@NonNull
	public Observable<Integer> usingTransaction(Func2<Subscription, BriteDatabase, Integer> updateAction) {
		return Observable.create(SyncOnSubscribe.createSingleState(Subscriptions::empty, (subs, observer) -> {
			Transaction t = base.newTransaction();
			//noinspection TryFinallyCanBeTryWithResources
			try {
				int updateCount = updateAction.call(subs, base);
				if (!subs.isUnsubscribed()) {
					t.markSuccessful();
					observer.onNext(updateCount);
					observer.onCompleted();
				}
			} finally {
				t.close();
			}
		}, Subscription::unsubscribe));
	}

	/**
	 * @see BriteDatabase#createQuery(String, String, String...)
	 */
	@CheckResult
	@NonNull
	public QueryObservable createQuery(@NonNull String table, @NonNull String sql, @NonNull String... args) {
		return base.createQuery(table, sql, args);
	}

	/**
	 * @see BriteDatabase#createQuery(Iterable, String, String...)
	 */
	@CheckResult
	@NonNull
	public QueryObservable createQuery(@NonNull Iterable<String> tables, @NonNull String sql, @NonNull String... args) {
		return base.createQuery(tables, sql, args);
	}

	/**
	 * @see BriteDatabase#insert(String, ContentValues)
	 */
	public Observable<Long> createInsert(@NonNull String table, @NonNull ContentValues values) {
		return Observable.fromCallable(() -> base.insert(table, values));
	}

	/**
	 * @see BriteDatabase#insert(String, ContentValues, int)
	 */
	public Observable<Long> createInsert(@NonNull String table, @NonNull ContentValues values, @ConflictAlgorithm int conflictAlgorithm) {
		return Observable.fromCallable(() -> base.insert(table, values, conflictAlgorithm));
	}

	/**
	 * @see BriteDatabase#delete(String, String, String...)
	 */
	public Observable<Integer> createDelete(@NonNull String table, @Nullable String whereClause, @Nullable String... whereArgs) {
		return Observable.fromCallable(() -> base.delete(table, whereClause, whereArgs));
	}

	/**
	 * @see BriteDatabase#update(String, ContentValues, String, String...)
	 */
	public Observable<Integer> createUpdate(@NonNull String table, @NonNull ContentValues values, @Nullable String whereClause, @Nullable String... whereArgs) {
		return Observable.fromCallable(() -> base.update(table, values, whereClause, whereArgs));
	}

	/**
	 * @see BriteDatabase#update(String, ContentValues, int, String, String...)
	 */
	public Observable<Integer> createUpdate(@NonNull String table, @NonNull ContentValues values, @ConflictAlgorithm int conflictAlgorithm, @Nullable String whereClause, @Nullable String... whereArgs) {
		return Observable.fromCallable(() -> base.update(table, values, conflictAlgorithm, whereClause, whereArgs));
	}

	/**
	 * @see BriteDatabase#execute(String)
	 */
	public Observable<Void> createExecSql(String sql) {
		return Observable.fromCallable(() -> {
			base.execute(sql);
			return null;
		});
	}

	/**
	 * @see BriteDatabase#execute(String, Object...)
	 */
	public Observable<Void> createExecSql(String sql, Object... args) {
		return Observable.fromCallable(() -> {
			base.execute(sql, args);
			return null;
		});
	}

	/**
	 * @see BriteDatabase#executeAndTrigger(String, String)
	 */
	public Observable<Void> createExecSqlWithTrigger(String table, String sql) {
		return Observable.fromCallable(() -> {
			base.executeAndTrigger(table, sql);
			return null;
		});
	}

	/**
	 * @see BriteDatabase#executeAndTrigger(String, String, Object...)
	 */
	public Observable<Void> createExecSqlWithTrigger(String table, String sql, Object... args) {
		return Observable.fromCallable(() -> {
			base.executeAndTrigger(table, sql, args);
			return null;
		});
	}

	// ======================================
	// Delegate methods
	// ======================================

	/**
	 * @see BriteDatabase#query(String, String...)
	 */
	@CheckResult
	public Cursor query(@NonNull String sql, @NonNull String... args) {
		return base.query(sql, args);
	}

	/**
	 * @see BriteDatabase#newTransaction()
	 */
	@CheckResult
	@NonNull
	public Transaction newTransaction() {
		return base.newTransaction();
	}

	/**
	 * @see BriteDatabase#insert(String, ContentValues)
	 */
	public long insert(@NonNull String table, @NonNull ContentValues values) {
		return base.insert(table, values);
	}

	/**
	 * @see BriteDatabase#insert(String, ContentValues, int)
	 */
	public long insert(@NonNull String table, @NonNull ContentValues values, @ConflictAlgorithm int conflictAlgorithm) {
		return base.insert(table, values, conflictAlgorithm);
	}

	/**
	 * @see BriteDatabase#delete(String, String, String...)
	 */
	public int delete(@NonNull String table, @Nullable String whereClause, @Nullable String... whereArgs) {
		return base.delete(table, whereClause, whereArgs);
	}

	/**
	 * @see BriteDatabase#update(String, ContentValues, String, String...)
	 */
	public int update(@NonNull String table, @NonNull ContentValues values, @Nullable String whereClause, @Nullable String... whereArgs) {
		return base.update(table, values, whereClause, whereArgs);
	}

	/**
	 * @see BriteDatabase#update(String, ContentValues, int, String, String...)
	 */
	public int update(@NonNull String table, @NonNull ContentValues values, @ConflictAlgorithm int conflictAlgorithm, @Nullable String whereClause, @Nullable String... whereArgs) {
		return base.update(table, values, conflictAlgorithm, whereClause, whereArgs);
	}

	/**
	 * @see BriteDatabase#execute(String)
	 */
	public void execute(String sql) {
		base.execute(sql);
	}

	/**
	 * @see BriteDatabase#execute(String, Object...)
	 */
	public void execute(String sql, Object... args) {
		base.execute(sql, args);
	}

	/**
	 * @see BriteDatabase#executeAndTrigger(String, String)
	 */
	public void executeAndTrigger(String table, String sql) {
		base.executeAndTrigger(table, sql);
	}

	/**
	 * @see BriteDatabase#executeAndTrigger(String, String, Object...)
	 */
	public void executeAndTrigger(String table, String sql, Object... args) {
		base.executeAndTrigger(table, sql, args);
	}
}
