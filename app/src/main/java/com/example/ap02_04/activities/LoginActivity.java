package com.example.ap02_04.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ap02_04.R;
import com.example.ap02_04.api.ClientAPI;
import com.example.ap02_04.entities.User;
import com.example.ap02_04.entities.UserPass;
import com.example.ap02_04.webservices.WebChat;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText inputUsername;
    EditText inputPassword;
    TextView tvRegister;

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