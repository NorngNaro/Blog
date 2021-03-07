package com.naro.blog.api;



import com.naro.blog.api.request.CreateArticleRequest;
import com.naro.blog.api.response.BaseResponse;
import com.naro.blog.model.Article;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ArticleService {

    @GET("articles")
    Call<BaseResponse<List<Article>>> findAll(@Query("page") int page, @Query("limit") int limit);

    @GET("articles/{id}")
    Call<BaseResponse<Article>> findById(@Path("id") int id);

    @POST("articles")
    Call<BaseResponse<Article>> createNew(@Body CreateArticleRequest article);

    @PUT("articles/{id}")
    Call<BaseResponse<Article>> updateById(@Path("id") int id, @Body CreateArticleRequest article);

    @DELETE("articles/{id}")
    Call<BaseResponse<Article>> deleteById(@Path("id") int id);

}
