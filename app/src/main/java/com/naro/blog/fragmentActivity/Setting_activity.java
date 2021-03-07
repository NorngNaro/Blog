package com.naro.blog.fragmentActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.facebook.login.LoginManager;
import com.naro.blog.Activity.Edit_profile_Activity;
import com.naro.blog.Activity.Flash_screen_Activity;
import com.naro.blog.Activity.Home_Activity;
import com.naro.blog.Activity.MainActivity;
import com.naro.blog.R;

public class Setting_activity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View setting = inflater.inflate(R.layout.activity_setting, container, false);

        AppCompatTextView logout = setting.findViewById(R.id.log_out);
        AppCompatTextView edit = setting.findViewById(R.id.edit_acc);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                save_Login();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Edit_profile_Activity.class);
                startActivity(intent);
                Home_Activity home = new Home_Activity();
                home.closeHome();
            }
        });

        return setting;
    }

    private void save_Login(){

        SharedPreferences prefs = this.getActivity().getSharedPreferences(Flash_screen_Activity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor myEditor = prefs.edit();

        myEditor.putBoolean(String.valueOf(Flash_screen_Activity.LOGIN),false);
        myEditor.apply();
    }
}
