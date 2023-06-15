package com.example.ap02_04.api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenAPI {

    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    private String token;

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


    public String getToken(String username, String password) {

        Call<String> call = webServiceAPI.getToken(username, password);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                token = response.body();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
        return token;
    }

}

