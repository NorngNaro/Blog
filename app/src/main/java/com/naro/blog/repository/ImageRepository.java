package com.naro.blog.repository;

import androidx.lifecycle.MutableLiveData;


import com.naro.blog.api.response.ImageUploadResponse;

import okhttp3.MultipartBody;

public interface ImageRepository {

    MutableLiveData<ImageUploadResponse> uploadSingle(MultipartBody.Part file);

}
