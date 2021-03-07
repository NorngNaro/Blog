package com.naro.blog.fragmentActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.naro.blog.Activity.View_Activity;
import com.naro.blog.Adapter.ListMyBlogAdapter;
import com.naro.blog.R;
import com.naro.blog.api.request.CreateArticleRequest;
import com.naro.blog.Activity.CreateArticleActivity;
import com.naro.blog.model.Article;
import com.naro.blog.model.Pagination;
import com.naro.blog.utils.PaginationUtil;
import com.naro.blog.viewmodel.ArticleViewModel;

import java.util.ArrayList;

public class Myblog_Activity extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ListMyBlogAdapter.OnActionClick{

    private ListMyBlogAdapter listMyBlogAdapter;
    private LinearLayoutManager linearLayoutManager;

    private boolean isReload = false;
    private boolean isLastPage = false;
    private boolean isLoading = false;
    private boolean isCreate = false;
    private boolean isUpdate = false;
    private int currentItemUpdate = 0;

    private Pagination pagination;
    private View myBlog;
    private ArticleViewModel articleViewModel;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myBlog = inflater.inflate(R.layout.activity_myblog,container,false);

        configRecyclerView();

        SwipeRefreshLayout swipe = myBlog.findViewById(R.id.swipe);

        swipe.setOnRefreshListener(this);


        articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class);
        articleViewModel.init();

        getArticleLiveData();

        return myBlog;

    }

    @Override
    public void onStart() {
        super.onStart();
        if (isCreate) {
            onRefresh();
        }
        if (isUpdate) {
            Log.d("TAG", "onStart: update");
            listMyBlogAdapter.getDataSet().get(currentItemUpdate).setTitle("New Title");
            listMyBlogAdapter.notifyItemChanged(currentItemUpdate);
            isUpdate = false;
        }
    }

    private void configRecyclerView() {

        pagination = new Pagination();

        RecyclerView recyclerView = myBlog.findViewById(R.id.my_blog_recycler);

        linearLayoutManager = new LinearLayoutManager(getContext());
        listMyBlogAdapter = new ListMyBlogAdapter( getContext(), new ArrayList<>(),this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(listMyBlogAdapter);

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

        ProgressBar progressBar = myBlog.findViewById(R.id.progressBar);
        SwipeRefreshLayout swipe = myBlog.findViewById(R.id.swipe);

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
            listMyBlogAdapter.setDataSet(listBaseResponse.getData());

            // set behavior to default
            isReload = false;
            isLoading = false;
        });

    }

    @Override
    public void onRefresh() {
        // clear data set in adapter
        listMyBlogAdapter.getDataSet().clear();
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
        Intent intent = new Intent(getContext(), View_Activity.class);
        intent.putExtra("article", listMyBlogAdapter.getDataSet().get(position));
        Log.e("TAG", "onItemClick: "+ listMyBlogAdapter.getDataSet().get(position));
        startActivity(intent);
    }

    @Override
    public void onEdit(int position) {
        isUpdate = true;

        currentItemUpdate = position;
        Article articleDataSet = listMyBlogAdapter.getDataSet().get(position);
        CreateArticleRequest article = new CreateArticleRequest();

        int id = articleDataSet.getId();
        article.setTitle(articleDataSet.getTitle());
        article.setDescription(articleDataSet.getDescription());
        article.setImage(articleDataSet.getImageUrl());

        Intent intent = new Intent(getContext(), CreateArticleActivity.class);
        intent.putExtra("article", article);
        intent.putExtra("id", id);
        startActivity(intent);
    }


    @Override
    public void onDelete(int position) {
        new AlertDialog.Builder(getContext())
                .setTitle("1 Blog will be deleted.")
                .setPositiveButton("Delete", (dialog, which) -> {
                    int id = listMyBlogAdapter.getDataSet().get(position).getId();
                    articleViewModel.deleteById(id).observe(this, articleBaseResponse -> {
                        Toast.makeText(getContext(), articleBaseResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        listMyBlogAdapter.notifyItemRemoved(position);
                    });
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(true)
                .show();
    }


}
