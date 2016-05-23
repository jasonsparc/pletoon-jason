package com.jasonsparc.pletoon.client;

import com.google.gson.Gson;
import com.jasonsparc.pletoon.core.Main;
import com.jasonsparc.pletoon.client.db.PletoonDB;
import com.jasonsparc.pletoon.sqlbrite.BriteDatabase2;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by jasonsparc on 5/21/2016.
 */
public class Pletoon {

	private static final Gson gson = Main.graph.getGson();

	private static final PletoonAPI api = Main.graph.getPletoonAPI();

	private static final OkHttpClient client = Main.graph.getClient();

	private static final Retrofit retrofit = Main.graph.getRetrofit();

	private static final PletoonDB db = Main.graph.getPletoonDB();

	public static Gson gson() {
		return gson;
	}

	public static PletoonAPI api() {
		return api;
	}

	public static OkHttpClient client() {
		return client;
	}

	public static Retrofit retrofit() {
		return retrofit;
	}

	public static PletoonDB db() {
		return db;
	}

	public static BriteDatabase2 rawdb() {
		return db.rawdb();
	}
}
