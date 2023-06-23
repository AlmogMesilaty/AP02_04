package com.example.ap02_04.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ap02_04.entities.ChatLite;
import com.example.ap02_04.entities.NewChat;
import com.example.ap02_04.repositories.ChatsRepository;

import java.util.List;

public class ChatsViewModel extends ViewModel {

    // declarations for all the variables in the view model
    private ChatsRepository mRepository;

    // defines chats as a mutable live data to enable chats activity to observe changes in it
    private MutableLiveData<List<ChatLite>> chats;

    // constructor for the view model class initializes new chats repository object
    public ChatsViewModel () {
        mRepository  = new ChatsRepository();

        // getting the chats from the data handling section
        chats = mRepository.getChats();
    }

    public MutableLiveData<List<ChatLite>> getChats() { return chats; }

    public void add(NewChat newChat) { mRepository.addChat(newChat); }

    public void delete(int id) { mRepository.deleteChat(id); }

    public void search(String text) { chats.setValue(mRepository.search(text).getValue()); }

//    public void reload() { mRepository.reloadChats(); }

}
