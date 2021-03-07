package com.naro.blog.repository.impl;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import com.naro.blog.api.ImageService;
import com.naro.blog.api.config.RetrofitConfig;
import com.naro.blog.api.response.ImageUploadResponse;
import com.naro.blog.repository.ImageRepository;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageRepositoryImpl implements ImageRepository {

    private final ImageService imageService;

    public ImageRepositoryImpl() {
        imageService = RetrofitConfig.createService(ImageService.class);
    }

    @Override
    public MutableLiveData<ImageUploadResponse> uploadSingle(MultipartBody.Part file) {

        MutableLiveData<ImageUploadResponse> liveData = new MutableLiveData<>();
        Call<ImageUploadResponse> call = imageService.uploadSingle(file);

        call.enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(Call<ImageUploadResponse> call, Response<ImageUploadResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("response", "" + response.body());
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ImageUploadResponse> call, Throwable t) {
                Log.e("fail", t.getLocalizedMessage());
            }
        });

        return liveData;
    }

}
