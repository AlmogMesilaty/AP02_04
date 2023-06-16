package com.example.ap02_04.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap02_04.R;
import com.example.ap02_04.adapters.MessagesListAdapter;

public class MessagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        RecyclerView lstMessages = findViewById(R.id.lstMessages);
        // creates new adapter
        final MessagesListAdapter adapter = new MessagesListAdapter(this);
        // link recycler view with its adapter
        lstMessages.setAdapter(adapter);
        // making sure the element will appear in a linear form
        lstMessages.setLayoutManager(new LinearLayoutManager(this));

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChatsActivity.class);
            startActivity(intent);
        });

//        User user1 = new User("aaa@gmail.com","1234456789a", "Alice", R.drawable.image1);
//        User user2 = new User("bbb@gmail.com","1234456789a", "Bob", R.drawable.image2);
//
//        Message msg1 = new Message(1, "13/06/2023", user1,"Hello!");
//        Message msg2 = new Message(2, "13/06/2023", user2,"World");
//        Message msg3 = new Message(1, "13/06/2023", user1,"Hello!");
//        Message msg4 = new Message(2, "13/06/2023", user2,"World");
//        Message msg5 = new Message(1, "13/06/2023", user2,"Hello!");
//        Message msg6 = new Message(2, "13/06/2023", user1,"World");
//
//        List<Message> messages = new ArrayList<>();
//        messages.add(msg1);
//        messages.add(msg2);
//        messages.add(msg3);
//        messages.add(msg4);
//        messages.add(msg5);
//        messages.add(msg6);
//
//        adapter.setMessages(messages);
    }
}