package com.example.ap02_04.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ap02_04.entities.ServerUrl;

@Dao
public interface ServerUrlDao {

    @Query("SELECT * FROM serverUrl WHERE 1 = 1")
    ServerUrl get();

    @Insert
    void insert(ServerUrl url);

    @Update
    void update(ServerUrl url);

    @Query("DELETE FROM serverUrl")
    void clear();

}
