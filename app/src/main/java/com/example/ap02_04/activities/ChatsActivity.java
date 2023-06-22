package com.example.ap02_04.activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatsActivity extends AppCompatActivity implements ChatsListInterface {

    // declarations for all the variables in the chats activity
    private ChatsListAdapter adapter;
    private ChatsViewModel chatsViewModel;
    private RecyclerView lstChats;
    private ImageButton btnSettings;
    private FloatingActionButton btnAdd;
    private EditText etSearch;

    private ImageView ivProfilePic;
    private TextView tvDisplayName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        // firebase
        FirebaseMessaging.getInstance().subscribeToTopic("Messages")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Done";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                    }
                }
        });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        Toast.makeText(ChatsActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

//        // get the token of app - Hemi
//        FirebaseInstancedId.getInstance().getInstanceId().addOnSuccessListener(ChatsActivity.this, instanceIdResult -> {
//                String newToken = instanceIdResult.getToken();
//        });


        // connect each variable with its corresponding component in the xml file
        lstChats = findViewById(R.id.lstChats);
        adapter = new ChatsListAdapter(this, this);
        lstChats.setAdapter(adapter);
        lstChats.setLayoutManager(new LinearLayoutManager(this));

        btnSettings = findViewById(R.id.btnSettings);
        btnAdd = findViewById(R.id.btnAdd);
        etSearch = findViewById(R.id.etSearch);
        chatsViewModel = new ChatsViewModel(this);

        ivProfilePic = findViewById(R.id.ivProfilePic);
        tvDisplayName = findViewById(R.id.tvDisplayName);

        WebChat.setContext(this);

        // loads the user details
        loadUserDetails();

        // defines settings button behavior
        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });

        // defines add button behavior
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddChatActivity.class);
            startActivity(intent);
        });

        // asks form the view model to get all the chats of the current logged user
        chatsViewModel.getChats().observe(this, chats -> {
            adapter.setChats(chats);
            adapter.notifyDataSetChanged();
        });

        // listen to the search field, defines its behavior on text change
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                chatsViewModel.search(etSearch.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etSearch.getText().toString().trim().isEmpty()) {
                    chatsViewModel.search("");
                }
            }
        });

        // defines the chats list item behavior when swiped left
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(lstChats);

    }


    // method for printing messages to the screen
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    // method for getting full details of specific chat
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


    // recognize which chat has been clicked, and call get chat to get its details
    @Override
    public void onItemClick(int position) {
        WebChat.setContact(adapter.getItem(position).getUser());
        WebChat.setChatLite(adapter.getItem(position));
        getChat();
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

    // presents the user detail in the activity
    private void loadUserDetails() {
        tvDisplayName.setText(WebChat.getUser().getDisplayName());
        byte[] bytes = Base64.decode(WebChat.getUser().getProfilePic(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        ivProfilePic.setImageBitmap(bitmap);
    }

}

