package com.example.ap02_04.repositories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ap02_04.entities.Chat;

import java.util.List;

@Dao
public interface ChatDao {

    @Query("SELECT * FROM chat WHERE id IN (SELECT id FROM chat WHERE users LIKE :username)")
    List<Chat> getAll(String username);

    @Query("SELECT * FROM chat WHERE id IN (SELECT id FROM chat WHERE users LIKE '%' || :displayName || '%' AND users NOT LIKE '%' || :username )")
    List<Chat> search(String displayName, String username);

    @Insert
    void insert(Chat... chats);

    @Update
    void update(Chat... chats);

    @Delete
    void delete(Chat... chats);

}
