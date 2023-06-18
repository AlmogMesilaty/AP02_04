package com.example.ap02_04.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap02_04.R;
import com.example.ap02_04.adapters.MessagesListAdapter;
import com.example.ap02_04.entities.NewMessage;
import com.example.ap02_04.viewmodels.MessagesViewModel;
import com.example.ap02_04.webservices.WebChat;

public class MessagesActivity extends AppCompatActivity {

    private ImageButton btnBack;
    private ImageView ivProfilePic;
    private TextView tvDisplayName;
    private ImageButton btnSend;
    private EditText etTextBox;

    private MessagesViewModel messagesViewModel;

    RecyclerView lstMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        WebChat.setContext(getApplicationContext());

        btnBack = findViewById(R.id.btnBack);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        tvDisplayName = findViewById(R.id.tvDisplayName);
        btnSend = findViewById(R.id.btnSend);
        etTextBox = findViewById(R.id.etTextBox);

        messagesViewModel = new MessagesViewModel();

        lstMessages = findViewById(R.id.lstMessages);
        final MessagesListAdapter adapter = new MessagesListAdapter(this);
        lstMessages.setAdapter(adapter);
        lstMessages.setLayoutManager(new LinearLayoutManager(this));

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChatsActivity.class);
            startActivity(intent);
        });

        // observe and set adapter when live data is changed
        messagesViewModel.getMessages().observe(this, messages -> {
            adapter.setMessages(messages);
        });

        btnSend.setOnClickListener(v -> {
            if (!etTextBox.getText().toString().trim().isEmpty()) {
                messagesViewModel.add(new NewMessage(etTextBox.getText().toString()));
            }
        });

    }

    private void loadUserDetails() {
        tvDisplayName.setText(WebChat.getContact().getDisplayName());
        byte[] bytes = Base64.decode(WebChat.getContact().getProfilePic(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        ivProfilePic.setImageBitmap(bitmap);
    }
}