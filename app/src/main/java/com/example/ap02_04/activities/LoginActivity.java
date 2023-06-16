package com.example.ap02_04.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ap02_04.R;
import com.example.ap02_04.api.ClientAPI;
import com.example.ap02_04.entities.UserPass;
import com.example.ap02_04.webservices.WebChat;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText etUsername;
    EditText etPassword;
    Button noAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.loginButton);
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        noAccount = findViewById(R.id.noAccount);


        noAccount.setOnClickListener(view -> {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        });


        btnLogin.setOnClickListener(v -> {

            if (TextUtils.isEmpty(etUsername.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())) {
                String message = "Enter username and password";
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            } else {
                UserPass user = new UserPass(etUsername.getText().toString(), etPassword.getText().toString());
                loginUser(user);
            }
        });


    }

    public void loginUser(UserPass user) {
        Call<ResponseBody> call = ClientAPI.getService().getToken(user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    WebChat.setToken(response.body().toString());
                    WebChat.setUsername(user.getUsername());
                    String message = "Login successful!";
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, ChatsActivity.class));
                } else {
                    String message = "An error occurred";
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

}