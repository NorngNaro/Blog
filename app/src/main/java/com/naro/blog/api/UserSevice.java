package com.naro.blog.api;

import com.naro.blog.api.request.CreateUserRequest;
import com.naro.blog.api.response.UserResponse;
import com.naro.blog.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserSevice {

    @POST("user")
    Call<UserResponse<User>> createNew(@Body CreateUserRequest userRequest);

}
