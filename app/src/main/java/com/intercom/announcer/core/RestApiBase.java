package com.intercom.announcer.core;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public abstract class RestApiBase {
    protected String baseUrl;
    protected OkHttpClient httpClient;
    protected int connectionTimeout = 10; // in second
    protected int writeTimeout = 10; // second
    protected int readTimeout = 30; // in second
    protected Retrofit api;

    protected abstract void build();
}
