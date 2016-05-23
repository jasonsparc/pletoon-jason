package com.jasonsparc.pletoon.client.db;

import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.CheckResult;

import com.jasonsparc.pletoon.client.cookies.CookieEntry;
import com.jasonsparc.pletoon.rx.Sched;
import com.jasonsparc.pletoon.sqlbrite.BriteDatabase2;
import com.squareup.sqlbrite.BriteDatabase.ConflictAlgorithm;
import com.squareup.sqlbrite.QueryObservable;
import com.squareup.sqlbrite.SqlBrite;

import java.util.List;

import okhttp3.Cookie;
import rx.Observable;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;

/**
 * Created by jasonsparc on 4/22/2016.
 */
public class PletoonDB {
	public static final int VERSION = PletoonOpenHelper.DATABASE_VERSION;
	public static final String NAME = PletoonOpenHelper.DATABASE_NAME;

	final BriteDatabase2 db;

	PletoonDB(SqlBrite sqlBrite, SQLiteOpenHelper openHelper) {
		this.db = new BriteDatabase2(sqlBrite.wrapDatabaseHelper(openHelper, Sched.immediate()));
	}

	public BriteDatabase2 rawdb() {
		return db;
	}

	// ==========================================
	// Cookie handling
	// ==========================================

	@CheckResult
	public Observable<Integer> saveValidCookies(List<Cookie> cookies) {
		return insertValidCookies(cookies, CONFLICT_REPLACE);
	}

	@CheckResult
	public Observable<List<Cookie>> listCookies(String host, String path) {
		return observeCookieEntries(host, path)
				.flatMap(q -> q.asRows(CookieEntry.mapper())
						.map(CookieEntry::body)
						.toList())
				.first();
	}

	@CheckResult
	public Observable<Integer> insertValidCookies(List<Cookie> cookies, @ConflictAlgorithm int conflictAlgorithm) {
		return db.usingTransaction((subs, db) -> {
			final long now = System.currentTimeMillis();
			for (Cookie cookie : cookies) {
				if (subs.isUnsubscribed()) return 0;
				if (cookie.expiresAt() < now) continue;
				db.insert(CookieEntry.TABLE_NAME, CookieEntry.create(cookie).asContentValues(), conflictAlgorithm);
			}
			return cookies.size();
		});
	}

	@CheckResult
	public Observable<Integer> deleteExpiredCookies(String host, String path) {
		return db.createDelete(CookieEntry.TABLE_NAME, CookieEntry.DELETE_EXPIRED_WHERE_CLAUSE, host, host, path, path);
	}

	/**
	 * WARNING: This will continue to notify subscribers of changes until they unsubscribe.
	 */
	@CheckResult
	public QueryObservable observeCookieEntries(String host, String path) {
		return db.createQuery(CookieEntry.TABLE_NAME, CookieEntry.QUERY_ENTRIES, host, host, path, path);
	}
}
