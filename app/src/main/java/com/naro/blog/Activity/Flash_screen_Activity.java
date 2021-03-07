package com.naro.blog.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.widget.AppCompatTextView;

import com.naro.blog.R;

public class Flash_screen_Activity extends Activity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String LOGIN = "login";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String BIO = "bio";
    public static final String IMAGE_URL = "image_url";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);

        SharedPreferences prefs = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(prefs.getBoolean(LOGIN,false)){
                    Intent intent = new Intent(Flash_screen_Activity.this,Home_Activity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(Flash_screen_Activity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        }, 2000);





    }
}
