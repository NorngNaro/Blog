package com.naro.blog.repository;

import androidx.lifecycle.MutableLiveData;

import com.naro.blog.api.request.CreateArticleRequest;
import com.naro.blog.api.response.BaseResponse;
import com.naro.blog.model.Article;
import com.naro.blog.model.Pagination;

import java.util.List;

public interface ArticleRepository {

    MutableLiveData<BaseResponse<List<Article>>> findAll(Pagination pagination);
    MutableLiveData<BaseResponse<Article>> createNew(CreateArticleRequest article);
    MutableLiveData<BaseResponse<Article>> deleteById(int id);
    MutableLiveData<BaseResponse<Article>> updateById(int id, CreateArticleRequest article);

}
