package com.naro.blog.api.response;



import com.naro.blog.model.Article;
import com.naro.blog.model.Pagination;

import java.io.Serializable;
import java.util.List;

public class BaseResponse<D> implements Serializable {

    private int code;
    private String message;
    private D data;
    private Pagination pagination;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Article> getData() {
        return (List<Article>) data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public Pagination getPagination() {
        return pagination;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", pagination=" + pagination +
                '}';
    }
}
