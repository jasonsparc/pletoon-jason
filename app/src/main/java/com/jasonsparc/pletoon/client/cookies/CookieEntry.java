package com.jasonsparc.pletoon.client.cookies;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.gabrielittner.auto.value.cursor.ColumnAdapter;
import com.gabrielittner.auto.value.cursor.ColumnTypeAdapter;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.ryanharter.auto.value.parcel.ParcelAdapter;

import java.io.IOException;

import okhttp3.Cookie;
import rx.functions.Func1;

/**
 * Created by jasonsparc on 5/22/2016.
 */
@AutoValue
public abstract class CookieEntry implements Parcelable {

	public abstract String name();

	public abstract String domain();

	public abstract String path();

	public abstract long expiresAt();

	@ParcelAdapter(CookieAdapter.class)
	@ColumnAdapter(CookieAdapter.class)
	public abstract Cookie body();

	public static CookieEntry create(@NonNull Cookie cookie) {
		String domain = cookie.domain();
		// Remove the starting dot character of the domain, if exists (e.g: .domain.com -> domain.com)
		if (domain.charAt(0) == '.') {
			domain = domain.substring(1);
		}
		return new AutoValue_CookieEntry(cookie.name(), domain, cookie.path(), cookie.expiresAt(), cookie);
	}

	// ======================================
	// Database handling and mapping
	// ======================================

	public static final String TABLE_NAME = "cookie_entry";

	public static final String CREATE_TABLE = ""
			+ "CREATE TABLE IF NOT EXISTS cookie_entry (\n"
			+ "\tname TEXT NOT NULL COLLATE NOCASE,\n"
			+ "\tdomain TEXT NOT NULL COLLATE NOCASE,\n"
			+ "\tpath TEXT NOT NULL,\n"
			+ "\texpiresAt INTEGER NOT NULL,\n"
			+ "\tbody BLOB NOT NULL,\n"
			+ "\tPRIMARY KEY(name, domain, path)\n"
			+ ")";

	public static final String QUERY_ENTRIES = ""
			+ "SELECT *\n"
			+ "FROM cookie_entry\n"
			+ "WHERE (\n"
			+ "\t''||expiresAt >= strftime('%s', 'now')\n"
			+ ") AND (\n"
			/**
			 * http://tools.ietf.org/html/rfc6265#section-5.1.3
			 *
			 * A string domain-matches a given domain string if at least one of the
			 * following conditions hold:
			 *
			 * o  The domain string and the string are identical.  (Note that both
			 * the domain string and the string will have been canonicalized to
			 * lower case at this point.)
			 *
			 * o  All of the following conditions hold:
			 *
			 *     *  The domain string is a suffix of the string.
			 *
			 *     *  The last character of the string that is not included in the
			 *     domain string is a %x2E (\".\") character.
			 *
			 *     *  The string is a host name (i.e., not an IP address).
			 */
			+ "\n"
			+ "\t(domain = /*{host}*/?) OR (/*{host}*/? LIKE ('%.'||domain))\n"
			+ ") AND (\n"
			/**
			 * http://tools.ietf.org/html/rfc6265#section-5.1.4
			 *
			 * A request-path path-matches a given cookie-path if at least one of
			 * the following conditions holds:
			 *
			 * o  The cookie-path and the request-path are identical.
			 *
			 * o  The cookie-path is a prefix of the request-path, and the last
			 * character of the cookie-path is %x2F (\"/\").
			 *
			 * o  The cookie-path is a prefix of the request-path, and the first
			 * character of the request-path that is not included in the cookie-
			 * path is a %x2F (\"/\") character.
			 */
			+ "\t(/*{path}*/? GLOB (path||'*')) AND (( path GLOB '*/') OR (/*{path}*/? GLOB path||'/*'))\n"
			+ ")";

	public static final String DELETE_EXPIRED_WHERE_CLAUSE = ""
			+ "(\n"
			+ "\t''||expiresAt < strftime('%s', 'now')\n"
			+ ") AND (\n"
			+ "\t(domain = /*{host}*/?) OR (/*{host}*/? LIKE ('%.'||domain))\n"
			+ ") AND (\n"
			+ "\t(/*{path}*/? GLOB (path||'*')) AND (( path GLOB '*/') OR (/*{path}*/? GLOB path||'/*'))\n"
			+ ")";

	public static CookieEntry create(Cursor cursor) {
		return AutoValue_CookieEntry.createFromCursor(cursor);
	}

	public static Func1<Cursor, CookieEntry> mapper() {
		return AutoValue_CookieEntry.MAPPER;
	}

	public abstract ContentValues asContentValues();

	// ======================================
	// Gson TypeAdapters
	// ======================================

	public static TypeAdapter<CookieEntry> typeAdapter(Gson gson) {
		return new AutoValue_CookieEntry.GsonTypeAdapter(gson);
	}

	public static TypeAdapter<Cookie> typeAdapterForCookie(Gson gson) {
		return new CookieAdapter();
	}

	// ======================================
	// `Cookie` to `String` handling
	// ======================================

	static class CookieAdapter extends TypeAdapter<Cookie> implements ColumnTypeAdapter<Cookie>, com.ryanharter.auto.value.parcel.TypeAdapter<Cookie> {
		static final String SEP = "\n";

		@Override
		public void write(JsonWriter out, Cookie value) throws IOException {
			out.value(convertToString(value));
		}

		@Override
		public Cookie read(JsonReader in) throws IOException {
			if (in.peek() == JsonToken.NULL) {
				in.nextNull();
				return null;
			}
			return convertFromString(in.nextString());
		}

		@Override
		public Cookie fromCursor(Cursor cursor, String columnName) {
			final int columnIndex = cursor.getColumnIndex(columnName);
			return cursor.isNull(columnIndex) ? null : convertFromString(cursor.getString(columnIndex));
		}

		@Override
		public void toContentValues(ContentValues values, String columnName, Cookie value) {
			values.put(columnName, convertToString(value));
		}

		@Override
		public void toParcel(Cookie value, Parcel dest) {
			dest.writeString(convertToString(value));
		}

		@Override
		public Cookie fromParcel(Parcel in) {
			return convertFromString(in.readString());
		}

		static String convertToString(Cookie model) {
			if (model == null) {
				return null;
			}
			StringBuilder sb = new StringBuilder();
			sb.append("name").append(SEP).append(model.name());
			sb.append(SEP).append("value").append(SEP).append(model.value());
			if (model.persistent())
				sb.append(SEP).append("expiresAt").append(SEP).append(model.expiresAt());
			sb.append(SEP).append(model.hostOnly() ? "hostOnlyDomain" : "domain").append(SEP).append(model.domain());
			sb.append(SEP).append("path").append(SEP).append(model.path());
			if (model.secure()) sb.append(SEP).append("secure").append(SEP).append(true);
			if (model.httpOnly()) sb.append(SEP).append("secure").append(SEP).append(true);
			return sb.toString();
		}

		static Cookie convertFromString(String data) {
			if (data == null) {
				return null;
			}
			Cookie.Builder builder = new Cookie.Builder();
			String[] split = TextUtils.split(data, SEP);
			for (int i = 0, len = split.length; i < len; i += 2) {
				String key = split[i];
				String value = split[i + 1];
				switch (key) {
					case "name":
						builder.name(value);
						break;
					case "value":
						builder.value(value);
						break;
					case "expiresAt":
						builder.expiresAt(Long.parseLong(value));
						break;
					case "domain":
						builder.domain(value);
						break;
					case "hostOnlyDomain":
						builder.hostOnlyDomain(value);
						break;
					case "path":
						builder.path(value);
						break;
					case "secure":
						builder.secure();
						break;
					case "httpOnly":
						builder.httpOnly();
						break;
				}
			}
			return builder.build();
		}
	}
}
