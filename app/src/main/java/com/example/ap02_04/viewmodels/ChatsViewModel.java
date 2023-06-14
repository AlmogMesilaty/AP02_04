package com.example.ap02_04.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ap02_04.entities.Chat;
import com.example.ap02_04.repositories.ChatsRepository;

import java.util.List;

public class ChatsViewModel extends ViewModel {
    // all interaction with server
    private ChatsRepository mRepository;
    // we are not changing the data, it's the repository purpose
    private LiveData<List<Chat>> chats;
    // current chat
    private LiveData<Chat> chat;

    public ChatsViewModel () {
        mRepository  = new ChatsRepository();
        // asks from the repository all the chats
        chats = mRepository.getChats();
    }

    // export the live data to the activities
    public LiveData<List<Chat>> getChats() { return chats; }

    //public Chat getChat(int id) { return mRepository.getChat(id); }

    public void add(Chat chat) { mRepository.addChat(chat); }

    public void delete(int id) { mRepository.deleteChat(id); }

    public void reload() { mRepository.reloadChats(); }

}
