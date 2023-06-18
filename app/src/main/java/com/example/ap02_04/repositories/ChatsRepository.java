package com.example.ap02_04.repositories;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.ap02_04.api.ChatAPI;
import com.example.ap02_04.entities.ChatLite;
import com.example.ap02_04.entities.NewChat;
import com.example.ap02_04.room.ChatDao;
import com.example.ap02_04.room.LocalDatabase;
import com.example.ap02_04.webservices.WebChat;

import java.util.LinkedList;
import java.util.List;

public class ChatsRepository {

    private LocalDatabase db;
    private ChatDao chatDao;

    private ChatListData chatListData;
    private ChatAPI chatAPI;


    public ChatsRepository() {
        db = Room.databaseBuilder(WebChat.getContext(), LocalDatabase.class, "ChatsDB").build();
        chatDao = db.chatDao();
        chatListData = new ChatListData();
        chatAPI = new ChatAPI();
    }

    class ChatListData extends MutableLiveData<List<ChatLite>> {

        public ChatListData() {
            super();
            setValue(new LinkedList<ChatLite>());
        }
        @Override
        protected void onActive() {
            super.onActive();
            new Thread(() -> {
//                chatDao.clear();
//                chatListData.postValue(chatDao.getChats(WebChat.getUsername()));
                chatAPI.getChats(chatListData);
            }).start();
        }
    }



    public MutableLiveData<List<ChatLite>> getChats() { return chatListData; }

    public void addChat(final NewChat newChat) { chatAPI.addChat(newChat, chatListData); }

    public void deleteChat(int id) { chatAPI.deleteChat(id, chatListData); }

    public void reloadChats() { chatAPI.getChats(chatListData); }

}
