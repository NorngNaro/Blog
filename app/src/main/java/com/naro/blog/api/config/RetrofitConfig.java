package com.naro.blog.api.config;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    private final static String BASE_URL = "http://110.74.194.124:15011/v1/api/";
    private final static String BASIC_TOKEN = "Basic QU1TQVBJQURNSU46QU1TQVBJUEBTU1dPUkQ=";

    private final static OkHttpClient.Builder client = new OkHttpClient.Builder()
            .addInterceptor(new BasicAuthInterceptor(BASIC_TOKEN));

    private static final Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create());

    private static final Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
