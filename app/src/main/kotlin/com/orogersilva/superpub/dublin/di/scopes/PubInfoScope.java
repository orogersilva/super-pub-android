package com.orogersilva.superpub.dublin.di.scopes;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by orogersilva on 5/2/2017.
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PubInfoScope {
}
