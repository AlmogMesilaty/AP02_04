package com.example.ap02_04.api;

import androidx.lifecycle.MutableLiveData;

import com.example.ap02_04.entities.UserPass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenAPI {

    private MutableLiveData<String> tokenData;
    //private TokenDao tokenDao
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;


    public TokenAPI() {
        // using builder to create retrofit object
        // convert JSON to object and vice versa
        retrofit = new Retrofit.Builder()
                //.baseUrl(WebChat.context.getString(R.string.BaseUrl))
                .baseUrl("http://10.0.2.2:5000/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // asking retrofit to create webService object that implements what we
        // specified in the interface
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }


    public void getToken(String username, String password) {
        UserPass userPass = new UserPass(username, password);
        Call<String> call = webServiceAPI.getToken(userPass);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                new Thread(() -> {
                    tokenData.postValue(response.body());
                }).start();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {}
        });
    }
}

