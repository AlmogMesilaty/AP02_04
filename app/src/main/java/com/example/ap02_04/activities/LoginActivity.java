package com.example.ap02_04.activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ap02_04.R;
import com.example.ap02_04.api.ClientAPI;
import com.example.ap02_04.entities.User;
import com.example.ap02_04.entities.UserPass;
import com.example.ap02_04.webservices.WebChat;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    MaterialButton btnLogin;
    EditText inputUsername;
    EditText inputPassword;
    MaterialTextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        WebChat.setContext(getApplicationContext());

        btnLogin = findViewById(R.id.btnLogin);
        inputUsername = findViewById(R.id.inputUsername);
        inputPassword = findViewById(R.id.inputPassword);
        tvRegister = findViewById(R.id.tvRegister);


        tvRegister.setOnClickListener(view -> {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        });


        btnLogin.setOnClickListener(v -> {
            if (isValidLoginDetails()){
                UserPass user = new UserPass(inputUsername.getText().toString(), inputPassword.getText().toString());
                loginUser(user);
            }
        });


        // firebase get token
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


        // handling notifications
        FirebaseMessaging.getInstance().subscribeToTopic("Messages")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Done";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                    }
                });
        

    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public Boolean isValidLoginDetails() {
        if (inputUsername.getText().toString().trim().isEmpty()) {
            showToast("Enter name");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputUsername.getText().toString()).matches()) {
            showToast("Enter valid Email");
            return false;
        } else if (inputPassword.getText().toString().trim().isEmpty()) {
            showToast("Enter password");
            return false;
        } else {
            return true;
        }
    }

    public void loginUser(UserPass user) {
        Call<ResponseBody> call = ClientAPI.getService().getToken(user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        WebChat.setToken(response.body().string());
                        WebChat.setUsername(user.getUsername());
                        getUser();
                    } catch (IOException e) {
                        showToast(e.getMessage());
                    }
                } else {
                    showToast("Wrong username / password");
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                showToast(t.getLocalizedMessage());
            }
        });
    }

    public void getUser() {
        Call<User> call = ClientAPI.getService().getUser(WebChat.getToken(), WebChat.getUsername());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    WebChat.setUser(response.body());
                    showToast("Login successful!");
                    startActivity(new Intent(LoginActivity.this, ChatsActivity.class));
                } else {
                    showToast(response.message());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showToast(t.getLocalizedMessage());
            }
        });
    }

}