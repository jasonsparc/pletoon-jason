package com.jasonsparc.pletoon.core;

import android.app.Application;

/**
 * Implemented by a class containing a method that receives an {@link Application}, used to perform
 * initializations during {@link Application#onCreate()}.
 * <p>
 * As a convention, the initializer method is always static and is always named {@code install}. And
 * the class containing the initializer method is always named {@code _initializer} when the
 * initializer method is its only public member.
 * <p>
 * This is merely a coding style or a pattern to segregate different initialization logic into
 * multiple modules. This is also useful when using the IDE's find usage command.
 * <p>
 * It is up to the {@link Application}'s implementation on how implementors of this interface are to
 * be used.
 * <p>
 * Created by jasonsparc on 1/24/2016.
 */
public interface Initializer {
}
