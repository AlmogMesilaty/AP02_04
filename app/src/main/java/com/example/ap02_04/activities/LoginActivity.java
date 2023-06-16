package com.example.ap02_04.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ap02_04.R;
import com.example.ap02_04.webservices.WebChat;

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

            // extract text from edit text in the activity
            WebChat.setUsername(etUsername.getText().toString());
            WebChat.setPassword(etPassword.getText().toString());

//            if (WebChat.getUsername() != "" && WebChat.getPassword() != "") {
//                tokenAPI.getToken(this);
//            }

        });


    }

//    public void login() {
//
//
//        // login unsuccessful
//        else {
//            // print message to the screen
//            Toast.makeText(LoginActivity.this, "Wrong username / password", Toast.LENGTH_SHORT).show();
//            EditText userTemp = findViewById(R.id.username);
//            userTemp.setText("");
//            EditText passwordTemp = findViewById(R.id.password);
//            passwordTemp.setText("");
//        }
//}

    public static void handleLogin(Context context) {
        if (WebChat.getToken() != null) {
            // get logged in user details
//            userAPI.getUser();
            // print message to the screen on
            Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();
//            // open chats activity
//            Intent intent = new Intent(this, ChatsActivity.class);
//            startActivity(intent);
        }
    }

    public static void loginError(Context context) {
        // print message to the screen
        Toast.makeText(context, "Wrong username / password", Toast.LENGTH_SHORT).show();
    }
}