package com.example.ap02_04.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.ap02_04.entities.Chat;
import com.example.ap02_04.entities.ChatLite;
import com.example.ap02_04.entities.Message;
import com.example.ap02_04.entities.ServerUrl;
import com.example.ap02_04.entities.User;

@Database(entities = {ServerUrl.class, ChatLite.class, Chat.class, Message.class, User.class}, version = 11)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract ChatDao chatDao();
    public abstract MessageDao messageDao();
    public abstract ServerUrlDao serverUrlDao();
}
