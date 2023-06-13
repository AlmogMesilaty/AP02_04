package com.example.ap02_04.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ap02_04.R;
import com.example.ap02_04.adapters.ChatsListAdapter;
import com.example.ap02_04.entities.Chat;
import com.example.ap02_04.entities.Message;
import com.example.ap02_04.entities.User;

import java.util.ArrayList;
import java.util.List;

public class ChatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView lstChats = findViewById(R.id.lstChats);
        // creates new adapter
        final ChatsListAdapter adapter = new ChatsListAdapter(this);
        // link recycler view with its adapter
        lstChats.setAdapter(adapter);
        // making sure the element will appear in a linear form
        lstChats.setLayoutManager(new LinearLayoutManager(this));

        User user1 = new User("aaa@gmail.com", "Alice", R.drawable.image1);
        User user2 = new User("bbb@gmail.com", "Bob", R.drawable.image2);
        User user3 = new User("ccc@gmail.com", "Carol", R.drawable.image3);
        User user4 = new User("ddd@gmail.com", "Dan", R.drawable.image4);
        User user5 = new User("eee@gmail.com", "Eran", R.drawable.image5);
        User user6 = new User("fff@gmail.com", "Foxy", R.drawable.image6);
        Message msg1 = new Message(1, "13/06/2023", user1,"Hello!");
        Message msg2 = new Message(2, "13/06/2023", user2,"World");
        Message msg3 = new Message(1, "13/06/2023", user3,"Hello!");
        Message msg4 = new Message(2, "13/06/2023", user4,"World");
        Message msg5 = new Message(1, "13/06/2023", user5,"Hello!");
        Message msg6 = new Message(2, "13/06/2023", user6,"World");
        List<Chat> chats = new ArrayList<>();

        List<User> users1 = new ArrayList<>();
        users1.add(user1);
        users1.add(user2);
        List<Message> messages1 = new ArrayList<>();
        messages1.add(msg1);
        messages1.add(msg2);
        chats.add(new Chat(1, users1, messages1));

        List<User> users2 = new ArrayList<>();
        users2.add(user3);
        users2.add(user4);
        List<Message> messages2 = new ArrayList<>();
        messages2.add(msg3);
        messages2.add(msg4);
        chats.add(new Chat(2, users2, messages2));

        List<User> users3 = new ArrayList<>();
        users3.add(user5);
        users3.add(user6);
        List<Message> messages3 = new ArrayList<>();
        messages3.add(msg5);
        messages3.add(msg6);
        chats.add(new Chat(3, users3, messages3));

        adapter.setChats(chats);


    }
}