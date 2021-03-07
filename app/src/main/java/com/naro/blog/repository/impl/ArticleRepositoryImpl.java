package com.naro.blog.repository.impl;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import com.naro.blog.api.ArticleService;
import com.naro.blog.api.config.RetrofitConfig;
import com.naro.blog.api.request.CreateArticleRequest;
import com.naro.blog.api.response.BaseResponse;
import com.naro.blog.model.Article;
import com.naro.blog.model.Pagination;
import com.naro.blog.repository.ArticleRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleRepositoryImpl implements ArticleRepository {

    private final static String TAG = ArticleRepositoryImpl.class.getName();

    private final ArticleService articleService;

    public ArticleRepositoryImpl() {
        articleService = RetrofitConfig.createService(ArticleService.class);
    }

    @Override
    public MutableLiveData<BaseResponse<List<Article>>> findAll(Pagination pagination) {

        MutableLiveData<BaseResponse<List<Article>>> data = new MutableLiveData<>();

        Call<BaseResponse<List<Article>>> call = articleService.findAll(pagination.getPage(), pagination.getLimit());

        call.enqueue(new Callback<BaseResponse<List<Article>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Article>>> call, Response<BaseResponse<List<Article>>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.e(TAG, response.body().getMessage());
                        data.setValue(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Article>>> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
            }
        });

        return data;
    }

    @Override
    public MutableLiveData<BaseResponse<Article>> createNew(CreateArticleRequest article) {

        MutableLiveData<BaseResponse<Article>> liveData = new MutableLiveData<>();

        articleService.createNew(article).enqueue(new Callback<BaseResponse<Article>>() {
            @Override
            public void onResponse(Call<BaseResponse<Article>> call, Response<BaseResponse<Article>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "response => " + response.body());
                    // logic
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Article>> call, Throwable t) {
                Log.e(TAG, "response => " + t.getLocalizedMessage());
            }
        });

        return liveData;
    }

    @Override
    public MutableLiveData<BaseResponse<Article>> deleteById(int id) {
        MutableLiveData<BaseResponse<Article>> liveData = new MutableLiveData<>();

        articleService.deleteById(id).enqueue(new Callback<BaseResponse<Article>>() {
            @Override
            public void onResponse(Call<BaseResponse<Article>> call, Response<BaseResponse<Article>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // your business logic here
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Article>> call, Throwable t) {
                Log.e("fail", t.getLocalizedMessage());
            }
        });

        return liveData;
    }

    @Override
    public MutableLiveData<BaseResponse<Article>> updateById(int id, CreateArticleRequest article) {

        MutableLiveData<BaseResponse<Article>> liveData = new MutableLiveData<>();

        articleService.updateById(id, article).enqueue(new Callback<BaseResponse<Article>>() {
            @Override
            public void onResponse(Call<BaseResponse<Article>> call, Response<BaseResponse<Article>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Article>> call, Throwable t) {
                Log.e("fail", t.getLocalizedMessage());
            }
        });

        return liveData;
    }
}
