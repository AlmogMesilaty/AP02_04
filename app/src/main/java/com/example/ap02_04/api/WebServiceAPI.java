package com.example.ap02_04.api;

import com.example.ap02_04.entities.Chat;
import com.example.ap02_04.entities.Message;
import com.example.ap02_04.entities.NewChat;
import com.example.ap02_04.entities.NewMessage;
import com.example.ap02_04.entities.User;
import com.example.ap02_04.entities.UserNoId;
import com.example.ap02_04.entities.UserPass;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {


    @GET("Chats")
    Call<List<Chat>> getChats(@Header("Authorization") String token);

    @POST("Chats")
    Call<Chat> addChat(@Header("Authorization") String token, @Body NewChat newChat);

    @GET("Chats/{id}")
    Call<Chat> getChat(@Header("Authorization") String token,@Path("id") int id);

    @DELETE("Chats/{id}")
    Call<Void> deleteChat(@Header("Authorization") String token, @Path("id") int id);

    @POST("Chats/{id}/Messages")
    Call<Message> addMessage(@Header("Authorization") String token, @Body NewMessage newMessage);

    @GET("Chats/{id}/Messages")
    Call<List<Message>> getMessages(@Header("Authorization") String token);

    @POST("Users")
    Call<Void> addUser(@Header("Authorization") String token, @Body UserNoId user);

    @GET("Users/{username}")
    Call<User> getUser(@Header("Authorization") String token, @Path("username") String  username);

    @POST("Tokens")
    Call<ResponseBody> getToken(@Body UserPass userPass);

}
