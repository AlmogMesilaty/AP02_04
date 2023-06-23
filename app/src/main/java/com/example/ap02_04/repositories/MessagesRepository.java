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
    private LocalDatabase db;
    private MessageDao messageDao;

    // object message api handles the requests from the remote server
    private MessageAPI messageAPI;
    private MessageListData messageListData;

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

//        reloadMessages();

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


    public MessageListData getMessages() {
//        reloadMessages();
        return messageListData;
    }

    public MessageListData addMessage(final NewMessage newMessage) {

        Thread thread1 = new Thread(() -> {
            messageAPI.addMessage(newMessage, messageListData);
        });
        thread1.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            return null;
        }

        Thread thread2 = new Thread(() -> {
            messageAPI.getMessages(messageListData);
        });
        thread2.start();
        try {
            thread2.join();
        } catch (InterruptedException e) {
            return null;
        }

        return messageListData;
    }

    public void reloadMessages() {

        Thread thread = new Thread(() -> {

            // set messages list to be the data that is in the local server
            messageListData.postValue(messageDao.getMessages());

        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) { }

        new Thread(() -> {

            // asks message api to fetch the fresh data from the remote server
            messageAPI.getMessages(messageListData);

        }).start();

    }

}
