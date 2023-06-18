package com.example.ap02_04.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ap02_04.entities.ChatLite;
import com.example.ap02_04.entities.NewChat;
import com.example.ap02_04.repositories.ChatsRepository;

import java.util.List;

public class ChatsViewModel extends ViewModel {

    private ChatsRepository mRepository;
    private MutableLiveData<List<ChatLite>> chats;

    public ChatsViewModel () {
        mRepository  = new ChatsRepository();
        chats = mRepository.getChats();
    }

    public MutableLiveData<List<ChatLite>> getChats() { return chats; }

    public void add(NewChat newChat) { mRepository.addChat(newChat); }

    public void delete(int id) { mRepository.deleteChat(id); }

//    public void reload() { mRepository.reloadChats(); }

}
