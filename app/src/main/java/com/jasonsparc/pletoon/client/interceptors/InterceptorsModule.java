package com.jasonsparc.pletoon.client.interceptors;

import com.jasonsparc.pletoon.Q;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.ElementsIntoSet;
import dagger.multibindings.IntoSet;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by jasonsparc on 5/22/2016.
 */
@Module
public class InterceptorsModule {

	@Provides
	@IntoSet
	@Named(Q.APPLICATION_INTERCEPTOR)
	Collection<Interceptor> providesApplicationInterceptors() {
		ArrayList<Interceptor> out = new ArrayList<>();
		out.add(CacheFallbackInterceptor.get());
		return out;
	}

	@Provides
	@IntoSet
	@Named(Q.NETWORK_INTERCEPTOR)
	Collection<Interceptor> providesNetworkInterceptors() {
		ArrayList<Interceptor> out = new ArrayList<>();
		out.add(AutoCachingInterceptor.get());
		return out;
	}

	@Provides
	@ElementsIntoSet
	@Named(Q.APPLICATION_INTERCEPTOR)
	Set<Collection<Interceptor>> providesDummyApplicationInterceptors() {
		return Collections.emptySet(); // A dummy in case we don't actually provide any.
	}

	@Provides
	@ElementsIntoSet
	@Named(Q.NETWORK_INTERCEPTOR)
	Set<Collection<Interceptor>> providesDummyNetworkInterceptors() {
		return Collections.emptySet(); // A dummy in case we don't actually provide any.
	}

	@Provides
	@Named(Q.LOGGING_INTERCEPTOR)
	Interceptor providesLoggingInterceptor() {
		AtomicHttpLoggingInterceptor logging = new AtomicHttpLoggingInterceptor();
		logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
		return logging;
	}
}
