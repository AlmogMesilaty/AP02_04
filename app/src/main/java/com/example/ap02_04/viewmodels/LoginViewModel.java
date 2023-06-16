package com.example.ap02_04.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.ap02_04.entities.User;
import com.example.ap02_04.repositories.LoginRepository;
import com.example.ap02_04.webservices.WebChat;

public class LoginViewModel extends ViewModel {

    private LoginRepository mRepository;

    private LiveData<String> token;

    private LiveData<User> user;

    public LoginViewModel() {
        mRepository = new LoginRepository();
        // asks the data from the repository
        user = mRepository.getUser(WebChat.getUsername());
        token = mRepository.getToken(WebChat.getUsername(), WebChat.getPassword());
    }

    public LiveData<User> getUser(String username) { return user; }

    public LiveData<String> getToken(String username, String password) { return token; }

}
