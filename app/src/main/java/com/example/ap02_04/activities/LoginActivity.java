package com.example.ap02_04.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ap02_04.R;
import com.example.ap02_04.entities.User;
import com.example.ap02_04.viewmodels.LoginViewModel;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    String username;
    String password;
    Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        List<User> users = new ArrayList<>();

        users.add(new User("aaa@gmail.com", "12345678a", "Alice0", 0));
        users.add(new User("bbb@gmail.com", "12345678b", "Alice1", 1));
        users.add(new User("ccc@gmail.com", "12345678c", "Alice2", 2));
        users.add(new User("ddd@gmail.com", "12345678d", "Alice3", 3));


        loginButton = findViewById(R.id.loginButton);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean connect = false;
                EditText userTemp = findViewById(R.id.username);
                username = userTemp.getText().toString();
                EditText passwordTemp = findViewById(R.id.username);
                password = passwordTemp.getText().toString();
                if (username != "" && password != "") {
                    LoginViewModel loginViewModel = new LoginViewModel(username, password);
                    String token = loginViewModel.getToken(username, password);
                    if (token != null) {
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(v.getContext(), ChatsActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

    }
}