package com.example.ap02_04.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap02_04.R;
import com.example.ap02_04.adapters.ChatsListAdapter;
import com.example.ap02_04.api.ChatAPI;
import com.example.ap02_04.viewmodels.ChatsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChatsActivity extends AppCompatActivity {

    private ChatsListAdapter adapter;
    private ChatsViewModel chatsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        RecyclerView lstChats = findViewById(R.id.lstChats);
        // creates new adapter
        adapter = new ChatsListAdapter(this);
        // link recycler view with its adapter
        lstChats.setAdapter(adapter);
        // making sure the element will appear in a linear form
        lstChats.setLayoutManager(new LinearLayoutManager(this));

        ImageButton btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });

        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddChatActivity.class);
            startActivity(intent);
        });

        ChatAPI chatAPI = new ChatAPI();
        //chatAPI.get();

        chatsViewModel = new ViewModelProvider(this).get(ChatsViewModel.class);

        // observe and set adapter when live data is changed
        chatsViewModel.get().observe(this, chats -> {
            adapter.setChats(chats);
        });

    }

}