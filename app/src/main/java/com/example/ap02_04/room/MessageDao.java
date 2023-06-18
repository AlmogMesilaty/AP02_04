package com.example.ap02_04.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ap02_04.entities.Message;

import java.util.List;

@Dao
public interface MessageDao {

    @Query("SELECT * FROM message")
    List<Message> getMessages();

    @Insert
    void insert(Message... messages);

    @Update
    void update(Message... messages);

    @Delete
    void delete(Message... messages);

}
