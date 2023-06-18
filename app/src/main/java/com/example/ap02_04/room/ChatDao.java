package com.example.ap02_04.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ap02_04.entities.Chat;

import java.util.List;

@Dao
public interface ChatDao {

    @Query("SELECT * FROM chat WHERE :username = :username")
    List<Chat> getChats(String username);

//    @Query("SELECT * FROM chat ")
//    List<Chat> search(String displayName, String username);

    @Query("SELECT * FROM chat WHERE id = :id")
    Chat getChat(int id);

    @Insert
    void insert(Chat... chats);

    @Update
    void update(Chat... chats);

    @Delete
    void delete(Chat... chats);

    @Query("DELETE FROM chat")
    void clear();

}
