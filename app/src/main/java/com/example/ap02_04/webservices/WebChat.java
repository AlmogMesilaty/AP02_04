package com.example.ap02_04.webservices;

import android.app.Application;
import android.content.Context;

import com.example.ap02_04.entities.Chat;
import com.example.ap02_04.entities.User;

public class WebChat extends Application {
    public static Context context;
    public static String token;
    public static User user;
    public static User contact;
    public static String username;
    public static Chat chat;


    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static void setContext(Context context) {
        WebChat.context = context;
    }


    public static void setUser(User user) {
        WebChat.user = user;
    }

    public static Context getContext() {
        return context;
    }

    public static void setToken(String token) { WebChat.token = "Bearer " + token; }

    public static String getToken() { return token; }

    public static User getUser() { return user; }

    public static String getUsername() { return username; }

    public static void setUsername(String username) { WebChat.username = username; }

    public static User getContact() { return contact; }

    public static void setContact(User contact) { WebChat.contact = contact; }

    public static Chat getChat() { return chat; }

    public static void setChat(Chat chat) { WebChat.chat = chat; }
}
