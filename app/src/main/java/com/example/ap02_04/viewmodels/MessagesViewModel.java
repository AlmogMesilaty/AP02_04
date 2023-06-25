package com.example.ap02_04.viewmodels;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ap02_04.entities.Message;
import com.example.ap02_04.entities.NewMessage;
import com.example.ap02_04.repositories.MessagesRepository;

import java.util.List;

public class MessagesViewModel {

    // instance of messages repository
    private MessagesRepository mRepository;

    // messages list as live data to enable messages activity to observe changes in it
    private MutableLiveData<List<Message>> messages;

    // constructor create instance of messages repo and asks for the messages list
    public MessagesViewModel (LifecycleOwner application) {
        mRepository  = new MessagesRepository();
        messages = mRepository.getMessages();
    }

    // returns the live data object
    public LiveData<List<Message>> getMessages() { return messages; }

    // adds new message
    public void add(NewMessage newMessage) {
        messages = mRepository.addMessage(newMessage);
    }

}
