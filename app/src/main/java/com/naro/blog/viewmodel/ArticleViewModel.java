package com.naro.blog.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.naro.blog.api.request.CreateArticleRequest;
import com.naro.blog.api.response.BaseResponse;
import com.naro.blog.model.Article;
import com.naro.blog.model.Pagination;
import com.naro.blog.repository.impl.ArticleRepositoryImpl;

import java.util.List;

public class ArticleViewModel extends ViewModel {

    private ArticleRepositoryImpl articleRepository;
    private MutableLiveData<BaseResponse<List<Article>>> data = new MutableLiveData<>();

    public void init() {
        this.articleRepository = new ArticleRepositoryImpl();
    }

    public MutableLiveData<BaseResponse<List<Article>>> getArticleLiveData(Pagination pagination) {
        return articleRepository.findAll(pagination);
    }

    public MutableLiveData<BaseResponse<Article>> createNewArticle(
            CreateArticleRequest article) {
        return articleRepository.createNew(article);
    }

    public MutableLiveData<BaseResponse<Article>> deleteById(int id) {
        return articleRepository.deleteById(id);
    }

    public MutableLiveData<BaseResponse<Article>> updateById(int id, CreateArticleRequest article) {
        return articleRepository.updateById(id, article);
    }


}
