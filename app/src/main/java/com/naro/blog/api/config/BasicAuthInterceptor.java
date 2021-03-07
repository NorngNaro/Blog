package com.naro.blog.api.config;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BasicAuthInterceptor implements Interceptor {

    private final String basicToken;

    public BasicAuthInterceptor(String basicToken) {
        this.basicToken = basicToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        Request.Builder builder = request.newBuilder()
                .header("Authorization", basicToken);

        request = builder.build();

        return chain.proceed(request);
    }

}
