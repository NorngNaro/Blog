package com.naro.blog.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pagination implements Serializable {

    private int page;
    private int limit;
    @SerializedName("total_count")
    private int totalCount;
    @SerializedName("total_pages")
    private int totalPages;

    public Pagination() {
        page = 1;
        limit = 15;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void increasePage() {
        this.page += 1;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "page=" + page +
                ", limit=" + limit +
                ", totalCount=" + totalCount +
                ", totalPages=" + totalPages +
                '}';
    }

}
