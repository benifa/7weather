package com.example.fabricebenimana.a7weather.service.base;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

final class RequestTypes {

    public static final int GET_RESULTS = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({GET_RESULTS})
    public @interface Interface {
    }

}
