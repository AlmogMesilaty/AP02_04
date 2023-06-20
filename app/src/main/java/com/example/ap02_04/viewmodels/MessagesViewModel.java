package com.example.ap02_04.viewmodels;

import androidx.lifecycle.LiveData;

import com.example.ap02_04.entities.Message;
import com.example.ap02_04.entities.NewMessage;
import com.example.ap02_04.repositories.MessagesRepository;

import java.util.List;

public class MessagesViewModel {

    // instance of messages repository
    private MessagesRepository mRepository;

    // messages list as live data to enable messages activity to observe changes in it
    private LiveData<List<Message>> messages;

    // constructor create instance of messages repo and asks for the messages list
    public MessagesViewModel () {
        mRepository  = new MessagesRepository();
        messages = mRepository.getMessages();
    }

    // export the live data to the activities
    public LiveData<List<Message>> getMessages() { return messages; }

    public void add(NewMessage newMessage) { mRepository.addMessage(newMessage); }

//    public void reload() { mRepository.reloadMessages(); }
}
