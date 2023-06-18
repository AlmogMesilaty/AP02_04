package com.example.ap02_04.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap02_04.R;
import com.example.ap02_04.adapters.ChatsListAdapter;
import com.example.ap02_04.viewmodels.ChatsViewModel;
import com.example.ap02_04.webservices.WebChat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChatsActivity extends AppCompatActivity {

    private ChatsListAdapter adapter;
    private ChatsViewModel chatsViewModel;
    private RecyclerView lstChats;
    private ImageButton btnSettings;
    private FloatingActionButton btnAdd;
    private SearchView svSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        WebChat.setContext(this);

        lstChats = findViewById(R.id.lstChats);
        adapter = new ChatsListAdapter(this);
        lstChats.setAdapter(adapter);
        lstChats.setLayoutManager(new LinearLayoutManager(this));

        btnSettings = findViewById(R.id.btnSettings);
        btnAdd = findViewById(R.id.btnAdd);
        svSearch = findViewById(R.id.svSearch);
        chatsViewModel = new ChatsViewModel();

        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddChatActivity.class);
            startActivity(intent);
        });

        chatsViewModel.getChats().observe(this, chats -> {
            adapter.setChats(chats);
        });


        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                // asks chats from the view model to give from the local ones
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

}