package com.naro.blog.repository;

import androidx.lifecycle.MutableLiveData;

import com.naro.blog.api.request.CreateUserRequest;

import com.naro.blog.api.response.UserResponse;
import com.naro.blog.model.User;

public interface UserRepository {
    MutableLiveData<UserResponse<User>> createNew(CreateUserRequest userRequest);


}
