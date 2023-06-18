package com.example.ap02_04.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ap02_04.entities.Chat;
import com.example.ap02_04.entities.NewChat;
import com.example.ap02_04.repositories.ChatsRepository;

import java.util.List;

public class ChatsViewModel extends ViewModel {

    private ChatsRepository mRepository;
    private MutableLiveData<List<Chat>> chats;
//    private LiveData<Chat> chat;

    public ChatsViewModel () {
        mRepository  = new ChatsRepository();
        chats = mRepository.getChats();
//        if (chats.getValue() != null) {
//            chat = mRepository.getChat(chats.getValue().get(0).getId());
//        }
    }

    public MutableLiveData<List<Chat>> getChats() { return chats; }

//    public LiveData<Chat> getChat(int id) { return chat; }

    public void add(NewChat newChat) { mRepository.addChat(newChat); }

    public void delete(int id) { mRepository.deleteChat(id); }

//    public void reload() { mRepository.reloadChats(); }

}
