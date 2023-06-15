package com.example.ap02_04.repositories;

import com.example.ap02_04.api.TokenAPI;
import com.example.ap02_04.api.UserAPI;
import com.example.ap02_04.entities.User;

public class LoginRepository {
    private final TokenAPI tokenAPI = new TokenAPI();

    private final UserAPI userAPI = new UserAPI();


    public User getUser(String username) {
        return userAPI.getUser(username);
    }

    public String getToken(String username, String password) {

        return tokenAPI.getToken(username, password);
    }


}
