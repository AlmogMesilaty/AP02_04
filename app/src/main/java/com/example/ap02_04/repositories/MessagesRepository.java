package com.example.ap02_04.repositories;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.ap02_04.api.MessageAPI;
import com.example.ap02_04.entities.Message;
import com.example.ap02_04.entities.NewMessage;
import com.example.ap02_04.room.LocalDatabase;
import com.example.ap02_04.room.MessageDao;
import com.example.ap02_04.webservices.WebChat;

import java.util.List;

public class MessagesRepository {
    private MessageDao messageDao;
    private MessagesRepository.MessageListData messageListData;
    private MessageAPI messageAPI;
    private LocalDatabase db;

    public MessagesRepository() {
        db = Room.databaseBuilder(WebChat.getContext(), LocalDatabase.class, "MessagesDB")
                .build();
        messageDao = db.messageDao();
        messageListData = new MessagesRepository.MessageListData();
        messageAPI = new MessageAPI();
    }

    class MessageListData extends MutableLiveData<List<Message>> {
        public MessageListData() {
            super();
            new Thread(() -> {
                List<Message> messages = messageDao.getMessages();
                postValue(WebChat.getChat().getMessages());
            }).start();
        }

        protected void onActive() {
            super.onActive();
            new Thread(() -> {
                messageListData.postValue(messageDao.getMessages());
            }).start();
        }
    }


    public MessagesRepository.MessageListData getMessages() {
        return messageListData;
    }

    public void addMessage(final NewMessage newMessage) { messageAPI.addMessage(newMessage); }

//    public void reloadMessages() { messageAPI.getMessages(); }
}
