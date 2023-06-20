package com.example.ap02_04.api;

import androidx.lifecycle.MutableLiveData;

import com.example.ap02_04.R;
import com.example.ap02_04.entities.Message;
import com.example.ap02_04.entities.NewMessage;
import com.example.ap02_04.webservices.WebChat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageAPI {

    // ADD THE ROOM FUNCTIONALITY

    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    private MutableLiveData<Message> newMessage;

    public MessageAPI() {
        // using builder to create retrofit object
        // convert JSON to object and vice versa
        retrofit = new Retrofit.Builder()
                //.baseUrl(WebChat.context.getString(R.string.BaseUrl))
                .baseUrl(WebChat.getContext().getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // asking retrofit to create webService object that implements what we
        // specified in the interface
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }


    // get all Messages
    public void getMessages(MutableLiveData<List<Message>> messages) {
        Call<List<Message>> call = webServiceAPI.getMessages(WebChat.getToken());
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {

                new Thread(() -> {
                    messages.postValue(response.body());
                }).start();
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) { }
        });
    }

    // add Message
    public void addMessage(final NewMessage newMessage) {
        Call<Message> call = webServiceAPI.addMessage(WebChat.getToken(), newMessage);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) { }

            @Override
            public void onFailure(Call<Message> call, Throwable t) { }
        });
    }

}
