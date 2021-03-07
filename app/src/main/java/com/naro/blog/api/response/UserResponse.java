package com.naro.blog.api.response;

import com.naro.blog.model.User;

import java.io.Serializable;
import java.util.List;

public class UserResponse<U> implements Serializable {

    private int code;
    private U data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<User> getData() {
        return (List<User>) data;
    }

    public void setData(U data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "code=" + code +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}

