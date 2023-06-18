package com.example.ap02_04.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap02_04.R;
import com.example.ap02_04.adapters.ChatsListAdapter;
import com.example.ap02_04.adapters.ChatsListInterface;
import com.example.ap02_04.api.ClientAPI;
import com.example.ap02_04.entities.Chat;
import com.example.ap02_04.viewmodels.ChatsViewModel;
import com.example.ap02_04.webservices.WebChat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatsActivity extends AppCompatActivity implements ChatsListInterface {

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
        adapter = new ChatsListAdapter(this, this);
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
            adapter.notifyDataSetChanged();
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

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(lstChats);

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAbsoluteAdapterPosition();
            chatsViewModel.delete(adapter.getItem(position).getId());
        }
    };

    public void getChat() {
        Call<Chat> call = ClientAPI.getService().getChat(WebChat.getToken(), WebChat.getChatLite().getId());
        call.enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, Response<Chat> response) {
                if (response.isSuccessful()) {
                    WebChat.setChat(response.body());
                    Intent i = new Intent(getApplicationContext(), MessagesActivity.class);
                    startActivity(i);
                } else {
                    showToast(response.message());
                }
            }
            @Override
            public void onFailure(Call<Chat> call, Throwable t) {
                showToast(t.getLocalizedMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onItemClick(int position) {
        WebChat.setContact(adapter.getItem(position).getUser());
        WebChat.setChatLite(adapter.getItem(position));
        getChat();
    }
}