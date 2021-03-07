package com.naro.blog.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.naro.blog.api.response.ImageUploadResponse;
import com.naro.blog.repository.impl.ImageRepositoryImpl;

import okhttp3.MultipartBody;

public class ImageViewModel extends ViewModel {

    private ImageRepositoryImpl imageRepository;
    private MutableLiveData<ImageUploadResponse> liveData;

    public void init() {
        imageRepository = new ImageRepositoryImpl();
    }

    public MutableLiveData<ImageUploadResponse> uploadSingle(MultipartBody.Part file) {
        return imageRepository.uploadSingle(file);
    }

}
