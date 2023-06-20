package com.example.ap02_04.repositories;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.ap02_04.api.MessageAPI;
import com.example.ap02_04.entities.Message;
import com.example.ap02_04.entities.NewMessage;
import com.example.ap02_04.room.LocalDatabase;
import com.example.ap02_04.room.MessageDao;
import com.example.ap02_04.webservices.WebChat;

import java.util.LinkedList;
import java.util.List;

public class MessagesRepository {

    // variables for handling room communication
    private MessageDao messageDao;
    private MessagesRepository.MessageListData messageListData;
    private LocalDatabase db;

    // object message api handles the requests from the remote server
    private MessageAPI messageAPI;

    // constructor
    public MessagesRepository() {

        // creates messages database inside the local server
        db = Room.databaseBuilder(WebChat.getContext(), LocalDatabase.class, "MessagesDB")
                .fallbackToDestructiveMigration()
                .build();

        // creates dao instance
        messageDao = db.messageDao();

        // create instance of message list data
        messageListData = new MessagesRepository.MessageListData();

        // creates instance of messages api class
        messageAPI = new MessageAPI();
    }

    // class that holds live data as an attribute
    class MessageListData extends MutableLiveData<List<Message>> {
        public MessageListData() {
            super();
            setValue(new LinkedList<Message>());
        }

        protected void onActive() {
            super.onActive();
            reloadMessages();
        }
    }


    public MessagesRepository.MessageListData getMessages() {
        return messageListData;
    }

    public void addMessage(final NewMessage newMessage) { messageAPI.addMessage(newMessage); }

    public void reloadMessages() {

        new Thread(() -> {
            // set messages list to be the data that is in the local server
            messageListData.postValue(messageDao.getMessages());

            // asks message api to fetch the fresh data from the remote server
            messageAPI.getMessages(messageListData);

        }).start();

    }

}
