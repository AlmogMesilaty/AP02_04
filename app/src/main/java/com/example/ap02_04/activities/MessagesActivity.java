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

    // declarations for all variables of messages activity
    private ImageButton btnBack;
    private ImageView ivProfilePic;
    private TextView tvDisplayName;
    private ImageButton btnSend;
    private EditText etTextBox;

    // creates message view model
    private MessagesViewModel messagesViewModel;

    // creates the messages recycler list
    RecyclerView lstMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        WebChat.setContext(this);

        // connect all the variables with their xml object compatibles
        btnBack = findViewById(R.id.btnBack);
        ivProfilePic = findViewById(R.id.ivProfilePic);
        tvDisplayName = findViewById(R.id.tvDisplayName);
        btnSend = findViewById(R.id.btnSend);
        etTextBox = findViewById(R.id.etTextBox);
        lstMessages = findViewById(R.id.lstMessages);

        // creates messages model
        messagesViewModel = new MessagesViewModel(this);

        // creates adapter for the messages list
        final MessagesListAdapter adapter = new MessagesListAdapter(this);
        lstMessages.setAdapter(adapter);
        lstMessages.setLayoutManager(new LinearLayoutManager(this));

        // set the messages to be the current ones in the WebChat
//        adapter.setMessages(WebChat.getChat().getMessages());
//        adapter.notifyDataSetChanged();

        // loads the user details
        loadUserDetails();

        // defines the behavior when back button is clicked
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChatsActivity.class);
            startActivity(intent);
        });

        // observe and set adapter when live data is changed
        messagesViewModel.getMessages().observe(this, messages -> {
            adapter.setMessages(messages);
            adapter.notifyDataSetChanged();
        });

        // defined the behavior when send button is clicked
        btnSend.setOnClickListener(v -> {
            if (!etTextBox.getText().toString().trim().isEmpty()) {
                messagesViewModel.add(new NewMessage(etTextBox.getText().toString()));
                etTextBox.setText("");
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        WebChat.setContext(this);
    }


    // presents the user detail in the activity
    private void loadUserDetails() {
        tvDisplayName.setText(WebChat.getContact().getDisplayName());
        byte[] bytes = Base64.decode(WebChat.getContact().getProfilePic(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        ivProfilePic.setImageBitmap(bitmap);
    }
}