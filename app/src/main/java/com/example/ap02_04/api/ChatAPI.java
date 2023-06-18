package com.example.ap02_04.api;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.ap02_04.R;
import com.example.ap02_04.entities.Chat;
import com.example.ap02_04.entities.ChatLite;
import com.example.ap02_04.entities.NewChat;
import com.example.ap02_04.room.ChatDao;
import com.example.ap02_04.room.LocalDatabase;
import com.example.ap02_04.webservices.WebChat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    private LocalDatabase db;
    private ChatDao chatDao;

    public ChatAPI() {
        // using builder to create retrofit object
        // convert JSON to object and vice versa
        retrofit = new Retrofit.Builder()
                .baseUrl(WebChat.getContext().getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // asking retrofit to create webService object that implements what we
        // specified in the interface
        webServiceAPI = retrofit.create(WebServiceAPI.class);

        db = Room.databaseBuilder(WebChat.getContext(), LocalDatabase.class, "ChatsDB")
                .build();
        chatDao = db.chatDao();

    }

    // get all chats
    public void getChats(MutableLiveData<List<ChatLite>> chats) {
        Call<List<ChatLite>> call = webServiceAPI.getChats(WebChat.getToken());
        call.enqueue(new Callback<List<ChatLite>>() {
            @Override
            public void onResponse(Call<List<ChatLite>> call, Response<List<ChatLite>> response) {
                new Thread(() -> {
//                    chatDao.clear();
//                    for ( Chat chat : response.body()) {
//                        chatDao.insert(chat);
//                    }
//                    chats.postValue(chatDao.getChats(WebChat.getUsername()));
                    chats.postValue(response.body());
                }).start();
            }

            @Override
            public void onFailure(Call<List<ChatLite>> call, Throwable t) { }
        });
    }

    // add chat
    public void addChat(final NewChat newChat, MutableLiveData<List<ChatLite>> chats) {
        Call<Chat> call = webServiceAPI.addChat(WebChat.getToken(), newChat);
        call.enqueue(new Callback<Chat>() {
            @Override
            public void onResponse(Call<Chat> call, Response<Chat> response) {
                new Thread(() -> {
//                    chatDao.insert(response.body());
//                    chats.postValue(chatDao.getChats(WebChat.getUsername()));
                    getChats(chats);
                }).start();
            }

            @Override
            public void onFailure(Call<Chat> call, Throwable t) { }
        });
    }

    // delete chat
    public void deleteChat(int id, MutableLiveData<List<ChatLite>> chats) {
        Call<Void> call = webServiceAPI.deleteChat(WebChat.getToken(), id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                new Thread(() -> {
                    //chatDao.delete(response.body());
                    getChats(chats);
                }).start();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) { }
        });
    }


}
