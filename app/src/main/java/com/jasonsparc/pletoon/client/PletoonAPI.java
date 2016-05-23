package com.jasonsparc.pletoon.client;

import com.jasonsparc.pletoon.model.ComicWrapper;
import com.jasonsparc.pletoon.model.Comics;
import com.jasonsparc.pletoon.model.Episode;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by jasonsparc on 5/21/2016.
 */
public interface PletoonAPI {
	String BASE_URL = "http://pletoon.com/api/";

	@GET("comics/")
	Observable<Comics> getComics();

	@GET("comics/{id}")
	Observable<ComicWrapper> getComic(@Path("id") String comicId);

	@GET("episodes/{id}")
	Observable<Episode> getEpisode(@Path("id") String episodeId);
}
