package com.naro.blog.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.naro.blog.R;
import com.naro.blog.databinding.ActivityCreateBlogBinding;
import com.naro.blog.databinding.ActivityEditProfileBinding;

public class Edit_profile_Activity extends AppCompatActivity {

    ActivityEditProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        btn_back();
        btn_save();

        SharedPreferences prefs = getSharedPreferences(Flash_screen_Activity.MyPREFERENCES,MODE_PRIVATE);

        binding.editUsername.setText(prefs.getString(Flash_screen_Activity.USERNAME,null));
        binding.editEmail.setText(prefs.getString(Flash_screen_Activity.EMAIL,null));
        binding.editBio.setText(prefs.getString(Flash_screen_Activity.BIO,null));
        Glide.with(this)
                .load(prefs.getString(Flash_screen_Activity.IMAGE_URL,null))
                .into(binding.imageProfile);





    }

    private void btn_save(){
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getSharedPreferences(Flash_screen_Activity.MyPREFERENCES,MODE_PRIVATE);
                SharedPreferences.Editor myEditor = prefs.edit();

                myEditor.putBoolean(Flash_screen_Activity.LOGIN, true);
                myEditor.putString(Flash_screen_Activity.USERNAME, String.valueOf(binding.editUsername.getText()));
                myEditor.putString(Flash_screen_Activity.EMAIL,String.valueOf(binding.editEmail.getText()));
                myEditor.putString(Flash_screen_Activity.BIO,String.valueOf(binding.editBio.getText()));
                myEditor.apply();

                finish();

            }
        });
    }

    private void btn_back(){
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}