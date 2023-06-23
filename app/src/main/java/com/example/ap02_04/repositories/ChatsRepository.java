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

    // declarations for all the variables in the chats repository
    private LocalDatabase db;
    private ChatDao chatDao;

    // variables that wraps the live data
    private ChatListData chatListData;
    private ChatAPI chatAPI;


    public ChatsRepository() {

        // creates the objects needed for communication with room
        db = Room.databaseBuilder(WebChat.getContext(), LocalDatabase.class, "ChatsDB")
                .fallbackToDestructiveMigration()
                .build();
        chatDao = db.chatDao();

        // creates new objects from our defined classes
        chatListData = new ChatListData();
        chatAPI = new ChatAPI();

//        reloadChats();

    }


    // class that contains live data variable, allows her
    class ChatListData extends MutableLiveData<List<ChatLite>> {
        public ChatListData() {
            super();
            setValue(new LinkedList<ChatLite>());
        }

        @Override
        protected void onActive() {
            super.onActive();
            reloadChats();
        }
    }

    public MutableLiveData<List<ChatLite>> getChats() {
//        reloadChats();
        return chatListData;
    }

    public void addChat(final NewChat newChat) { chatAPI.addChat(newChat, chatListData); }

    public void deleteChat(int id) { chatAPI.deleteChat(id, chatListData); }

    public void reloadChats() {

        new Thread(() -> {

            // set chats list to be the data that is in the local server
            chatListData.postValue(chatDao.getChats(WebChat.getUsername()));

            // asks chat api the fetch the fresh data from the remote server
            chatAPI.getChats(chatListData);

        }).start();

    }

    public MutableLiveData<List<ChatLite>> search(String text) {
        Thread thread = new Thread(() -> {
            chatListData.postValue(chatDao.search(text));
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            return null;
        }
        return chatListData;
    }

}


