package com.naro.blog.api;



import com.naro.blog.api.response.ImageUploadResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ImageService {

    @Multipart
    @POST("uploadfile/single")
    Call<ImageUploadResponse> uploadSingle(@Part MultipartBody.Part image);

}
