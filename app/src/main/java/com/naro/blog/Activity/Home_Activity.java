package com.naro.blog.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.naro.blog.R;
import com.naro.blog.databinding.ActivityHomeBinding;
import com.naro.blog.fragmentActivity.Account_Activity;
import com.naro.blog.fragmentActivity.Discover_Activity;
import com.naro.blog.fragmentActivity.Myblog_Activity;
import com.naro.blog.fragmentActivity.Setting_activity;

public class Home_Activity extends AppCompatActivity {
    ActivityHomeBinding binding;
    private boolean isCreate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        onFabClick();


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,
                    new Discover_Activity()).commit();
        }

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.my_blog:
                            selectedFragment = new Myblog_Activity();
                            break;
                        case R.id.discover:
                            selectedFragment = new Discover_Activity();
                            break;
                        case R.id.setting:
                            selectedFragment = new Setting_activity();
                            break;
                        case R.id.account:
                            selectedFragment = new Account_Activity();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment,
                            selectedFragment).commit();

                    return true;
                }
            };


        private void onFabClick() {
        binding.fabBtn.setOnClickListener(v -> {
            isCreate = true;
            Intent intent = new Intent(this, CreateArticleActivity.class);
            startActivity(intent);
        });

    }

    public void closeHome (){
            finish();
    }

    }
