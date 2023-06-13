package com.example.ap02_04.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Chat {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private List<User> users;

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

    public User getContact(String username) {
        if (username.equals(users.get(0).getUsername())) {
            return users.get(1);
        } else {
            return users.get(0);
        }
    }

    public Message getLastMessage() {
        return messages.get(0);
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
