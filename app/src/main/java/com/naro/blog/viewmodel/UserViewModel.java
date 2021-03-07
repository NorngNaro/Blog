package com.naro.blog.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.naro.blog.api.request.CreateUserRequest;
import com.naro.blog.api.response.UserResponse;
import com.naro.blog.model.User;
import com.naro.blog.repository.impl.UserRepositoryImpl;

public class UserViewModel extends ViewModel {

    private UserRepositoryImpl userRepository;

    public void init(){
        this.userRepository = new UserRepositoryImpl();
    }

    public MutableLiveData<UserResponse<User>> createUser(CreateUserRequest userRequest){
        return userRepository.createNew(userRequest);
    }

}
