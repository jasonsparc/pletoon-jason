package com.jasonsparc.pletoon.client.cookies;

import com.jasonsparc.pletoon.client.Pletoon;
import com.jasonsparc.pletoon.rx.Sched;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * FIXME: Does not yet correctly handle non-persistent (transient) cookies.
 * <p>
 * Created by jasonsparc on 1/24/2016.
 */
public class DatabaseCookieJar implements CookieJar {

	@Override
	public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
		Pletoon.db().saveValidCookies(cookies).subscribe();
	}

	@Override
	public List<Cookie> loadForRequest(HttpUrl url) {
		String host = url.host();
		String path = url.encodedPath();

		// Fetch all matching cookies.
		List<Cookie> cookies = Pletoon.db().listCookies(host, path).toBlocking().single();

		Pletoon.db().deleteExpiredCookies(host, path)
				// Schedule cookie cleanup on a different thread
				.subscribeOn(Sched.io())
				.subscribe();

		return cookies;
	}
}
