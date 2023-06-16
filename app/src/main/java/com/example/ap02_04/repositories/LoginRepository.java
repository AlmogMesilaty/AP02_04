package com.example.ap02_04.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.ap02_04.R;
import com.example.ap02_04.api.TokenAPI;
import com.example.ap02_04.api.UserAPI;
import com.example.ap02_04.entities.User;
import com.example.ap02_04.webservices.WebChat;

public class LoginRepository {

    private TokenData tokenData;
    private UserData userData;

    private TokenAPI tokenAPI;
    private UserAPI userAPI;

    //private TokenDao tokenDao;
    //private UserDao userDao;

    public LoginRepository() {
        //LocalDatabase db = LocalDatabase.getInstance();
        //tokenDao = db.tokenDao();
        //userDao = db.userDao();

        tokenData = new TokenData();
        userData = new UserData();

        tokenAPI = new TokenAPI();
        userAPI = new UserAPI();

        tokenData.postValue(tokenAPI.getToken(WebChat.getUsername(), WebChat.getPassword()));
        userData.postValue(userAPI.getUser(WebChat.getUsername()));
    }

    class TokenData extends MutableLiveData<String> {
        public TokenData() {
            super();
            // initialize token to empty string
            setValue("");
        }
    }

    class UserData extends MutableLiveData<User> {
        public UserData() {
            super();
            // initialize user to default user
            setValue(new User("default", "1234", "Welcome!", R.drawable.image0));
        }
    }

//    @Override
//    protected void onActive() {
//        super.onActive();
//        new Thread(() -> {
//            userData.postValue(userDao.getUser());
//        }).start();
//    }

    public LiveData<String> getToken(String username, String password) { return tokenData; }

    public LiveData<User> getUser(String username) { return userData; }

}
