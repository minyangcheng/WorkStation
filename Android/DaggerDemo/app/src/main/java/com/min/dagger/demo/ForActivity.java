package com.min.dagger.demo;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by minyangcheng on 2017/8/9.
 */

@Scope
@Retention(RUNTIME)
public @interface ForActivity {
}
