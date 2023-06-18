package com.example.ap02_04.viewmodels;

import androidx.lifecycle.LiveData;

import com.example.ap02_04.entities.Message;
import com.example.ap02_04.entities.NewMessage;
import com.example.ap02_04.repositories.MessagesRepository;

import java.util.List;

public class MessagesViewModel {

    private MessagesRepository mRepository;
    private LiveData<List<Message>> messages;

    public MessagesViewModel () {
        mRepository  = new MessagesRepository();
        messages = mRepository.getMessages();
    }

    // export the live data to the activities
    public LiveData<List<Message>> getMessages() { return messages; }

    public void add(NewMessage newMessage) { mRepository.addMessage(newMessage); }

//    public void reload() { mRepository.reloadMessages(); }
}
