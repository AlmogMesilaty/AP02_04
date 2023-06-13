package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class SignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        Button btnLogin =findViewById(R.id.btnLogIn);
        btnLogin.setOnClickListener(v -> {
            // Intent i = new Intent(this, ChatScreen.class);
        });
    }
}