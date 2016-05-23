package com.jasonsparc.pletoon.client.db;

import com.jasonsparc.pletoon.core.MainApplication;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jasonsparc on 4/22/2016.
 */
@Module
public class DatabaseModule {

	@Provides
	@Singleton
	PletoonDB providesPletoonDB(SqlBrite sqlBrite, PletoonOpenHelper openHelper) {
		return new PletoonDB(sqlBrite, openHelper);
	}

	@Provides
	@Singleton
	SqlBrite providesSqlBrite() {
		return SqlBrite.create();
	}

	@Provides
	@Singleton
	PletoonOpenHelper providesPletoonOpenHelper(MainApplication application) {
		return new PletoonOpenHelper(application);
	}
}
