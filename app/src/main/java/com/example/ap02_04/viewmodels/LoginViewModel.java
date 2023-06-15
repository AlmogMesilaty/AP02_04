package com.example.ap02_04.viewmodels;

import com.example.ap02_04.entities.User;
import com.example.ap02_04.repositories.LoginRepository;

public class LoginViewModel {
    private LoginRepository mRepository;
    private User user;
    private String Token;

    public LoginViewModel(String username, String password) {
        mRepository = new LoginRepository();
        user = mRepository.getUser(username);
        Token = mRepository.getToken(username, password);
    }

    public User getUser(String username) {
        return mRepository.getUser(username);
    }

    public String getToken(String username, String password) {
        return mRepository.getToken(username, password);
    }
}
