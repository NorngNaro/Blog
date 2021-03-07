package com.naro.blog.fragmentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.naro.blog.Activity.Edit_profile_Activity;
import com.naro.blog.Activity.Flash_screen_Activity;
import com.naro.blog.R;
import com.naro.blog.databinding.ActivityAccountBinding;

public class Account_Activity extends Fragment{

    ImageView imageButton;
    TextView username;
    TextView bio;
    View account;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       account = inflater.inflate(R.layout.activity_account,container,false);
        // Init view
        imageButton = account.findViewById(R.id.image_profile);
        username = account.findViewById(R.id.username);
        bio = account.findViewById(R.id.bio);


        profile();
        set_profile(account);


        return account;
    }

    @Override
    public void onResume() {
        super.onResume();

        set_profile(account);

    }

    private void set_profile(View account){
        SharedPreferences sharedpreferences;
        SharedPreferences prefs = getContext().getSharedPreferences(Flash_screen_Activity.MyPREFERENCES,getContext().MODE_PRIVATE);

        Log.e("TAG", "set_profile: " + Flash_screen_Activity.USERNAME + Flash_screen_Activity.IMAGE_URL );

        username.setText(prefs.getString(Flash_screen_Activity.USERNAME,null));
        Glide.with(account)
                .load(prefs.getString(Flash_screen_Activity.IMAGE_URL,null))
                .into(imageButton);
        if(prefs.getString(Flash_screen_Activity.BIO,null)!= null){
            bio.setText(prefs.getString(Flash_screen_Activity.BIO,null));
        }


    }


    private void profile(){
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Edit_profile_Activity.class));
            }
        });
    }

}
