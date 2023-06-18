package com.example.ap02_04.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.ap02_04.converters.Converters;

import java.util.List;

@Entity
public class Chat {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @TypeConverters(Converters.class)
    private List<User> users;

    @TypeConverters(Converters.class)
    private List<Message> messages;

    public Chat(int id, List<User> users, List<Message> messages) {
        this.id = id;
        this.users = users;
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public List<User> getUsers() {
        return users;
    }

    public User getContact(@NonNull String username) {
        if (username.equals(users.get(0).getUsername())) {
            return users.get(1);
        } else {
            return users.get(0);
        }
    }

    public Message getLastMessage() {
        if (messages != null && !messages.isEmpty()){
            return messages.get(0);
        } else {
            return new Message(-1, "00:00", new User("WebChat", "1234", "Welcome!", ""), "Hey!");
        }
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
