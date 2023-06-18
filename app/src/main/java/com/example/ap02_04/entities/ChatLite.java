package com.example.ap02_04.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.ap02_04.converters.Converters;

@Entity
public class ChatLite {

    @PrimaryKey
    private int id;

    @TypeConverters(Converters.class)
    private User user;

    @TypeConverters(Converters.class)
    private Message lastMessage;


    public ChatLite(int id, User user, Message lastMessage) {
        this.id = id;
        this.user = user;
        this.lastMessage = lastMessage;
    }

    public int getId() { return id; }

    public User getUser() {
        return user;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

}
