package com.example.ap02_04.api;

import com.example.ap02_04.entities.Chat;
import com.example.ap02_04.entities.Message;
import com.example.ap02_04.entities.Token;
import com.example.ap02_04.entities.User;
import com.example.ap02_04.entities.UserNoId;
import com.example.ap02_04.entities.UserPass;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {


    @GET("Chats")
    Call<List<Chat>> getChats();

    @POST("Chats")
    Call<Void> addChat(@Body Chat chat);

    @GET("Chats/{id}")
    Call<Chat> getChat(int id);

    @DELETE("Chats/{id}")
    Call<Void> deleteChat(@Path("id") int id);

    @POST("Chats/{id}/Messages")
    Call<Void> addMessage(@Body Message message);

    @GET("Chats/{id}/Messages")
    Call<List<Message>> getMessages();

    @POST("Users")
    Call<Void> addUser(@Body UserNoId user);

    @GET("Users/{username}")
    Call<User> getUser(@Path("username") String  username);

    @POST("Tokens")
    Call<Token> getToken(@Body UserPass userPass);


}
