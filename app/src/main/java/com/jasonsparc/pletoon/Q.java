package com.jasonsparc.pletoon;

/**
 * A registry of strings used as {@link javax.inject.Named @Named} qualifier names in
 * Dagger-related injections.
 * <p>
 * <b>Notes</b>:
 * <br>
 * - {@code Q} stands for "Qualifier"
 * <br>
 * - This class uses single-letter naming (i.e. {@code Q}) to simplify references and usage, making
 * it very convenient to use anywhere.
 * <p>
 * Created by jasonsparc on 5/22/2016.
 */
public interface Q {
	String DEFAULT = "DEFAULT";
	String EXT_FILES_DIR = "ExtFiles-Dir";
	String GLIDE = "GLIDE";

	String APPLICATION_INTERCEPTOR = "ApplicationInterceptor";
	String NETWORK_INTERCEPTOR = "NetworkInterceptor";

	String LOGGING_INTERCEPTOR = "LogginInterceptor";
}
