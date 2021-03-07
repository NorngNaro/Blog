package com.naro.blog.fragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.naro.blog.Activity.Discover_view_Activity;
import com.naro.blog.Adapter.ListDiscoverAdapter;
import com.naro.blog.R;
import com.naro.blog.model.Pagination;
import com.naro.blog.utils.PaginationUtil;
import com.naro.blog.viewmodel.ArticleViewModel;

import java.util.ArrayList;

public class Discover_Activity extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ListDiscoverAdapter.OnActionClick{

    private ListDiscoverAdapter listArticleAdapter;
    private LinearLayoutManager linearLayoutManager;

    private boolean isReload = false;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private boolean isCreate = false;
    private boolean isUpdate = false;
    private int currentItemUpdate = 0;

    private Pagination pagination;
    View discover;

    private ArticleViewModel articleViewModel;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        discover = inflater.inflate(R.layout.activity_discover,container,false);

        configRecyclerView();

        SwipeRefreshLayout swipe = discover.findViewById(R.id.swipe);




        swipe.setOnRefreshListener(this);


        articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class);
        articleViewModel.init();

        getArticleLiveData();

        return discover;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (isCreate) {
            onRefresh();
        }
        if (isUpdate) {
            Log.d("TAG", "onStart: update");
            listArticleAdapter.getDataSet().get(currentItemUpdate).setTitle("New Title");
            listArticleAdapter.notifyItemChanged(currentItemUpdate);
            isUpdate = false;
        }
    }

    private void configRecyclerView() {

        pagination = new Pagination();

        RecyclerView recyclerView = discover.findViewById(R.id.rcv_list_article);

        linearLayoutManager = new LinearLayoutManager(getContext());
        listArticleAdapter = new ListDiscoverAdapter( getContext(), new ArrayList<>(),this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(listArticleAdapter);

        recyclerView.addOnScrollListener(new PaginationUtil(linearLayoutManager, pagination.getLimit()) {

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                pagination.increasePage();
                getArticleLiveData();
            }

            @Override
            public boolean isLastPage() {
                return pagination.getPage() == pagination.getTotalPages();
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

        });

    }

    private void getArticleLiveData() {

        ProgressBar progressBar = discover.findViewById(R.id.progressBar);
        SwipeRefreshLayout swipe = discover.findViewById(R.id.swipe);

        // show loading
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        }

        // view subscribes live data
        articleViewModel.getArticleLiveData(pagination).observe(this, listBaseResponse -> {
            // after get article live data is success
            progressBar.setVisibility(View.GONE);
            // set swipe loading to invisible
            swipe.setRefreshing(false);
            // modify data set
            listArticleAdapter.setDataSet(listBaseResponse.getData());
            // set behavior to default
            isReload = false;
            isLoading = false;
        });

    }

    @Override
    public void onRefresh() {
        // clear data set in adapter
        listArticleAdapter.getDataSet().clear();
        // set reload equals true
        isReload = true;
        // set create equals false
        isCreate = false;
        // You must set page = 1 when you are going to refresh
        pagination.setPage(1);
        // ហៅមុខងារដើម្បីចាប់យកទិន្នន័យឡើងវិញ
        getArticleLiveData();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), Discover_view_Activity.class);
        intent.putExtra("article", listArticleAdapter.getDataSet().get(position));
        intent.putExtra("position", position);
        startActivity(intent);
    }

}
