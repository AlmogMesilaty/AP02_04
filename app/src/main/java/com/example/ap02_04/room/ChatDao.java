package com.example.ap02_04.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ap02_04.entities.Chat;
import com.example.ap02_04.entities.ChatLite;

import java.util.List;

@Dao
public interface ChatDao {

    @Query("SELECT * FROM chatLite WHERE :username = :username")
    List<ChatLite> getChats(String username);

    @Query("SELECT * FROM chat WHERE id = :id")
    Chat getChat(int id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ChatLite... chatLite);

    @Update
    void update(ChatLite... chatLite);

    @Delete
    void delete(ChatLite... chatLite);

    @Query("DELETE FROM chatLite")
    void clear();

}
