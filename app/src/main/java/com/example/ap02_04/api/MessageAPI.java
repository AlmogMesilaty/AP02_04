package com.example.ap02_04.api;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.ap02_04.R;
import com.example.ap02_04.entities.Message;
import com.example.ap02_04.entities.NewMessage;
import com.example.ap02_04.room.LocalDatabase;
import com.example.ap02_04.room.MessageDao;
import com.example.ap02_04.webservices.WebChat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MessageAPI {

    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    private LocalDatabase db;
    private MessageDao messageDao;

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

        db = Room.databaseBuilder(WebChat.getContext(), LocalDatabase.class, "MessagesDB")
                .build();
        messageDao = db.messageDao();

    }


    // get all Messages
    public void getMessages(MutableLiveData<List<Message>> messages) {
        Call<List<Message>> call = webServiceAPI.getMessages(WebChat.getToken(), WebChat.getChat().getId());
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {

                new Thread(() -> {
                    messageDao.clear();
                    for (int j = response.body().size() - 1; j >= 0; j--) {
                        Message message = response.body().get(j);
                        messageDao.insert(message);
                    }
                    messages.postValue(messageDao.getMessages());
                }).start();

            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) { }
        });
    }

    // add Message
    public void addMessage(final NewMessage newMessage, MutableLiveData<List<Message>> messages) {
        Call<Message> call = webServiceAPI.addMessage(WebChat.getToken(), newMessage, WebChat.getChat().getId());
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                // call get messages to update the messages after adding new one
//                new Thread(() -> {
//                    getMessages(messages);
//                }).start();
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) { }
        });
    }

}
