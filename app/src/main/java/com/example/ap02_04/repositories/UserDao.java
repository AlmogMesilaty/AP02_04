package com.example.ap02_04.repositories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ap02_04.entities.User;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user WHERE username = :username")
    List<User> get(String username);

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User... users);
}
