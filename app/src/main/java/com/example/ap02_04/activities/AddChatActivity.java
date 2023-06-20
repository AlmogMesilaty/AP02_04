package com.example.ap02_04.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ap02_04.R;
import com.example.ap02_04.entities.NewChat;
import com.example.ap02_04.viewmodels.ChatsViewModel;

public class AddChatActivity extends AppCompatActivity {

    private Button btnAdd;
    private EditText inputUsername;
    private ImageButton btnBack;
    private ChatsViewModel chatsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chat);

        btnBack = findViewById(R.id.btnBack);
        btnAdd = findViewById(R.id.btnAdd);
        inputUsername = findViewById(R.id.inputUsername);
        chatsViewModel = new ChatsViewModel(this);

        btnAdd.setOnClickListener(v -> {
            if (isValidUsername()) {
                chatsViewModel.add(new NewChat(inputUsername.getText().toString()));
                finish();
            }
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChatsActivity.class);
            startActivity(intent);
        });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public Boolean isValidUsername() {
        if (inputUsername.getText().toString().trim().isEmpty()) {
            showToast("Enter username");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(inputUsername.getText().toString()).matches()) {
            showToast("Enter valid Email");
            return false;
        } else {
            return true;
        }
    }

}