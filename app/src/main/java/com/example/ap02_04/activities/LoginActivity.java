package com.example.ap02_04.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ap02_04.R;
import com.example.ap02_04.viewmodels.LoginViewModel;
import com.example.ap02_04.webservices.WebChat;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {

            boolean connect = false;

            // extract text from edit text in the activity
            EditText userTemp = findViewById(R.id.username);
            WebChat.setUsername(userTemp.getText().toString());
            EditText passwordTemp = findViewById(R.id.password);
            WebChat.setPassword(passwordTemp.getText().toString());

            if (WebChat.getUsername() != "" && WebChat.getPassword() != "") {

                // sign as an observer in view model and update the app token
                viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
                viewModel.getToken(WebChat.getUsername(), WebChat.getPassword()).observe(this, token -> {
                    WebChat.setToken(token);
                });

                if (WebChat.getToken() != null) {
                    // updates current user
                    viewModel.getUser(WebChat.getUsername()).observe(this, user -> {
                        WebChat.setUser(user);
                    });
                    // print message to the screen on
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    // open chats activity
                    Intent intent = new Intent(v.getContext(), ChatsActivity.class);
                    startActivity(intent);
                }
                // login unsuccessful
                else {
                    // print message to the screen
                    Toast.makeText(LoginActivity.this, "Wrong username / password", Toast.LENGTH_SHORT).show();
                    // clean username and password fields
                    userTemp.setText("");
                    passwordTemp.setText("");
                }
            }
        });

    }
}