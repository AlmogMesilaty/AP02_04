package com.example.ap02_04.converters;

import androidx.room.TypeConverter;

import com.example.ap02_04.entities.Message;
import com.example.ap02_04.entities.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    private static Gson gson = new Gson();

    @TypeConverter
    public static List<User> fromUserListString(String value) {
        Type listType = new TypeToken<List<User>>() {}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String toUserListString(List<User> users) {
        return gson.toJson(users);
    }

    @TypeConverter
    public static List<Message> fromMessageListString(String value) {
        Type listType = new TypeToken<List<Message>>() {}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String toMessageListString(List<Message> messages) {
        return gson.toJson(messages);
    }

    @TypeConverter
    public static User fromString(String value) {
        Type type = new TypeToken<User>() {}.getType();
        return gson.fromJson(value, type);
    }

    @TypeConverter
    public static String toString(User user) {
        return gson.toJson(user);
    }
}
