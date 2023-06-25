package com.example.ap02_04.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ServerUrl {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String serverUrl;

    public ServerUrl(String serverUrl) {
        this.id = 0;
        this.serverUrl = serverUrl;
    }

    public String getServerUrl() { return serverUrl; }

    public void setServerUrl(String serverUrl) { this.serverUrl = serverUrl; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
}
