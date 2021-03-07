package com.naro.blog.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.naro.blog.Adapter.ListDiscoverAdapter;
import com.naro.blog.R;
import com.naro.blog.api.request.CreateArticleRequest;
import com.naro.blog.databinding.ActivityDiscoverViewBinding;
import com.naro.blog.model.Article;

public class Discover_view_Activity extends AppCompatActivity {

    ActivityDiscoverViewBinding binding;
    private ListDiscoverAdapter listArticleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDiscoverViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // invoke new intent
        onNewIntent(getIntent());

        //  onEdit((Integer) getIntent().getSerializableExtra("position"));
        //back to list view
        btn_back();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Article article = (Article) intent.getSerializableExtra("article");
        if (article != null) {
            Log.e("Article", "onNewIntent: "+ article );
            setDetailViews(article);
        }
    }

    private void setDetailViews(Article article) {
        Glide.with(this)
                .load(article.getImageUrl())
                .into(binding.imageView);
        binding.date.setText(formatTo(article.getCreatedDate()));
        binding.title.setText(article.getTitle());
        binding.text.setText(article.getDescription());
    }

    private void btn_back(){
        ImageButton btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onEdit(int position) {


        Article articleDataSet = listArticleAdapter.getDataSet().get(position);
        CreateArticleRequest article = new CreateArticleRequest();

        int id = articleDataSet.getId();
        article.setTitle(articleDataSet.getTitle());
        article.setDescription(articleDataSet.getDescription());
        article.setImage(articleDataSet.getImageUrl());

        Intent intent = new Intent(this, CreateArticleActivity.class);
        intent.putExtra("article", article);
        intent.putExtra("id", id);
        startActivity(intent);


    }

    public String formatTo(String dateData) {
        String day , month , year ;
        day = " " + dateData.substring(4,6);
        month = "." + dateData.substring(6,8);
        year = "." + dateData.substring(0,4);
        return day + month + year;
    }
}