package com.example.ap02_04.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ap02_04.entities.ChatLite;
import com.example.ap02_04.entities.NewChat;
import com.example.ap02_04.repositories.ChatsRepository;

import java.util.ArrayList;
import java.util.List;

public class ChatsViewModel extends ViewModel {

    // declarations for all the variables in the view model
    private ChatsRepository mRepository;

    // defines chats as a mutable live data to enable chats activity to observe changes in it
    private MutableLiveData<List<ChatLite>> chats;

    // defines live data for the chats list after filtering
    private MutableLiveData<List<ChatLite>> filteredList;

    // constructor for the view model class initializes new chats repository object
    public ChatsViewModel () {
        mRepository  = new ChatsRepository();

        // getting the chats from the data handling section
        chats = mRepository.getChats();

        // sets filtered chats to all the chats
        filteredList = new MutableLiveData<>();
        filteredList.setValue(chats.getValue());

    }

    public MutableLiveData<List<ChatLite>> getChats() { return chats; }

    public void add(NewChat newChat) { mRepository.addChat(newChat); }

    public void delete(int id) { mRepository.deleteChat(id); }

    public MutableLiveData<List<ChatLite>> getFilteredList() { return filteredList; }

    public void search(String text) {
        ArrayList<ChatLite> temp = new ArrayList<ChatLite>();
        for (ChatLite chat : chats.getValue()) {
            if (chat.getUser().getDisplayName().startsWith(text)) {
                temp.add(chat);
            }
        }
        filteredList.setValue(temp);
    }

//    public void reload() { mRepository.reloadChats(); }

}
