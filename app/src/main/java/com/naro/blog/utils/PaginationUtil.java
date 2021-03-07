package com.naro.blog.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationUtil extends RecyclerView.OnScrollListener {

    public static final int START_PAGE = 1;
    private static int LIMIT;

    @NonNull
    private final LinearLayoutManager linearLayoutManager;

    public PaginationUtil(@NonNull LinearLayoutManager linearLayoutManager, int limit) {
        this.linearLayoutManager = linearLayoutManager;
        LIMIT = limit;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = linearLayoutManager.getChildCount();
        int totalItemCount = linearLayoutManager.getItemCount();
        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= LIMIT) {
                loadMoreItems();
            }
        }

    }

    protected abstract void loadMoreItems();
    public abstract boolean isLastPage();
    public abstract boolean isLoading();

}
