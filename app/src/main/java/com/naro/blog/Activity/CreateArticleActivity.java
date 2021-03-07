package com.naro.blog.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.naro.blog.R;
import com.naro.blog.api.request.CreateArticleRequest;
import com.naro.blog.api.response.BaseResponse;
import com.naro.blog.api.response.ImageUploadResponse;
import com.naro.blog.databinding.ActivityCreateBlogBinding;
import com.naro.blog.model.Article;
import com.naro.blog.utils.FileUtils;
import com.naro.blog.viewmodel.ArticleViewModel;
import com.naro.blog.viewmodel.ImageViewModel;


public class CreateArticleActivity extends AppCompatActivity {

    ActivityCreateBlogBinding binding;
    public static final int GET_CONTENT_CODE = 1000;
    private Uri imageUri;

    private ImageViewModel imageViewModel;
    private ArticleViewModel articleViewModel;

    private CreateArticleRequest articleForUpdate;
    private int id;
    private boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateBlogBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        onNewIntent(getIntent());

        requestPermission();

        // init view model
        imageViewModel = new ViewModelProvider(this).get(ImageViewModel.class);
        imageViewModel.init();
        articleViewModel = new ViewModelProvider(this).get(ArticleViewModel.class);
        articleViewModel.init();

        // bind event
        onImagePick();
        onCreateBtnClick();
        btn_back();

    }

    private void requestPermission() {

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            return;
        }

        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){

        }

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, GET_CONTENT_CODE);

    }

    private void onUploadImageObserve() {
        binding.progressBar.setVisibility(View.VISIBLE);
        imageViewModel
                .uploadSingle(FileUtils.uploadFile(this, imageUri))
                .observe(this, new Observer<ImageUploadResponse>() {
                    @Override
                    public void onChanged(ImageUploadResponse imageUploadResponse) {
                        Toast.makeText(CreateArticleActivity.this,
                                imageUploadResponse.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        binding.progressBar.setVisibility(View.GONE);
                        String title = binding.editTitle.getText().toString();
                        String desc = binding.editContent.getText().toString();

                        CreateArticleRequest article = new CreateArticleRequest();
                        article.setTitle(title);
                        article.setDescription(desc);
                        article.setImage(imageUploadResponse.getData());

                        Log.d("OnChanged", "onChanged: " + article);

                        onCreateArticleObserve(article);
                    }
                });
    }

    private void onCreateArticleObserve(CreateArticleRequest article) {
        articleViewModel.createNewArticle(article).observe(this, new Observer<BaseResponse<Article>>() {
            @Override
            public void onChanged(BaseResponse<Article> articleBaseResponse) {
                Toast.makeText(CreateArticleActivity.this,
                        articleBaseResponse.getMessage(),
                        Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void onUpdateArticleObserve(int id, CreateArticleRequest article) {
        articleViewModel.updateById(id, article).observe(this, new Observer<BaseResponse<Article>>() {
            @Override
            public void onChanged(BaseResponse<Article> articleBaseResponse) {
                Toast.makeText(CreateArticleActivity.this, articleBaseResponse.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void onImagePick() {
        binding.imageArticle.setOnClickListener(v -> {
            Intent intent = FileUtils.createGetContentIntent();
            startActivityForResult(intent, GET_CONTENT_CODE);
        });
    }

    private void onCreateBtnClick() {
        binding.buttonCreate.setOnClickListener(v -> {
            if (!isUpdate) {
                Log.d("TAG", "Create");
                onUploadImageObserve();
            } else {
                Log.d("TAG", "Update");
                articleForUpdate.setTitle(binding.editTitle.getText().toString());
                articleForUpdate.setDescription(binding.editContent.getText().toString());
                onUpdateArticleObserve(id, articleForUpdate);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_CONTENT_CODE
                && resultCode == RESULT_OK
                && data != null) {
            imageUri = data.getData();
            Log.d("MYTAG", "" + imageUri);
            binding.imageArticle.setImageURI(imageUri);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            id = intent.getIntExtra("id", 0);
            articleForUpdate = (CreateArticleRequest) intent.getSerializableExtra("article");
            if (id != 0) {
                isUpdate = true;
                binding.editTitle.setText(articleForUpdate.getTitle());
                binding.editContent.setText(articleForUpdate.getDescription());
                Log.d("IMAGE", articleForUpdate.getImage());
                Glide.with(this)
                        .load(articleForUpdate.getImage())
                        .into(binding.imageArticle);
                binding.buttonCreate.setText("Save");
            }

        }
    }
    private void btn_back(){
        ImageButton btnback = findViewById(R.id.btn_back);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}