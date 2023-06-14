package com.example.ap02_04.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.ap02_04.converters.Converters;

@Entity
public class Message {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String created;

    @TypeConverters(Converters.class)
    private User sender;

    private String content;

    public Message(int id, String created, User sender, String content) {
        this.id = id;
        this.created = created;
        this.sender = sender;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getCreated() {
        return created;
    }

    @TypeConverters(Converters.class)
    public User getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
