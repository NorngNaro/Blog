package com.naro.blog.repository.impl;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.naro.blog.api.UserSevice;
import com.naro.blog.api.config.RetrofitConfig;
import com.naro.blog.api.request.CreateUserRequest;
import com.naro.blog.api.response.UserResponse;
import com.naro.blog.model.User;
import com.naro.blog.repository.UserRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepositoryImpl implements UserRepository {

    private final UserSevice userSevice;

    public UserRepositoryImpl(){
        userSevice = RetrofitConfig.createService(UserSevice.class);
    }


    @Override
    public MutableLiveData<UserResponse<User>> createNew(CreateUserRequest userRequest) {
        MutableLiveData<UserResponse<User>> liveData = new MutableLiveData();
        userSevice.createNew(userRequest).enqueue(new Callback<UserResponse<User>>() {

            @Override
            public void onResponse(Call<UserResponse<User>> call, Response<UserResponse<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("TAG", "response => " + response.body());
                    // logic
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UserResponse<User>> call, Throwable t) {
                Log.e("TAG", "response => " + t.getLocalizedMessage());
            }
        });
            return liveData ;
     }

}
