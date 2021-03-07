package com.naro.blog.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.naro.blog.R;
import com.naro.blog.api.request.CreateUserRequest;
import com.naro.blog.api.response.BaseResponse;
import com.naro.blog.api.response.UserResponse;
import com.naro.blog.model.User;
import com.naro.blog.viewmodel.UserViewModel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "Test";
    CallbackManager mCallbackManager;
    String facebookUserId = "";
    LoginButton loginButton;
    ProgressBar progressBar;
    SharedPreferences sharedpreferences;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sign_up();

       progressBar = findViewById(R.id.progressBar);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        // init user model
        userViewModel = new UserViewModel();
        userViewModel.init();


        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
                progressBar.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);


    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                              updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {

        Log.i("USER", user.getDisplayName());
        Log.i("USER", user.getEmail());
        Log.i("USER", String.valueOf(user.getPhotoUrl()));

        save_Login(user);

        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setName(user.getDisplayName());
        userRequest.setEmail(user.getEmail());
        userRequest.setImage_url(String.valueOf(user.getPhotoUrl()));

        Log.e("TAG", "onChanged: " + userRequest);

        OnCreateUser(userRequest);


        Intent intent = new Intent(MainActivity.this,Home_Activity.class);
        startActivity(intent);

        finish();

    }

    private void sign_up(){
        AppCompatTextView sign_up = findViewById(R.id.sign_up);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Sign_up_Activity.class);
                startActivity(intent);
            }
        });
    }


    private void OnCreateUser(CreateUserRequest userRequest) {

        Log.e(TAG, "onChanged: NOT Workkkkkkkkkkkkk" );
        Log.e(TAG, "onChanged: NOT Workkkkkkkkkkkkk" +userRequest);

        userViewModel.createUser(userRequest).observe(this, new Observer<UserResponse<User>>() {
            @Override
            public void onChanged(UserResponse<User> userUserResponse) {
                Toast.makeText(MainActivity.this,
                        userUserResponse.getMessage(),
                        Toast.LENGTH_LONG).show();

                Log.e(TAG, "onChanged: Workkkkkkkkkkkkk" );
                Log.e(TAG, "onChanged: NOTdfdf Workkkkkkkkkkkkk" );

            }
        });
    }



    private void save_Login(FirebaseUser user){

        SharedPreferences prefs = getSharedPreferences(Flash_screen_Activity.MyPREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor myEditor = prefs.edit();

        myEditor.putBoolean(Flash_screen_Activity.LOGIN, true);
        myEditor.putString(Flash_screen_Activity.USERNAME,user.getDisplayName());
        myEditor.putString(Flash_screen_Activity.EMAIL,user.getEmail());
        myEditor.putString(Flash_screen_Activity.IMAGE_URL,String.valueOf(user.getPhotoUrl()));
        myEditor.apply();

    }




}