package com.example.ap02_04.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ap02_04.entities.Chat;

import java.util.List;

public class ChatsViewModel extends ViewModel {
    // all interaction with server
    //private ChatsRepository mRepository;
    // we are not changing the data, it's the repository purpose
    private LiveData<List<Chat>> chats;
    // current chat
    private LiveData<Chat> chat;

    public ChatsViewModel () {
        //mRepository  = new ChatsRepository();
        //chats = mRepository.getAll();
    }

    public LiveData<List<Chat>> get() { return chats; }

    //public Chat getById(int id) { return mRepository.getById(id); }

    //public void add(Chat chat) { mRepository.add(chat); }

    //public void delete(Chat chat) { mRepository.delete(chat); }

    //public void reload(Chat chat) { mRepository.reload(chat); }

}
