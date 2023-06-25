package com.example.ap02_04.api;

import com.example.ap02_04.webservices.WebChat;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientAPI {

    public static Retrofit getRetrofit() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient();

        // using builder to create retrofit object
        // convert JSON to object and vice versa
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WebChat.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;

    }

    public static WebServiceAPI getService() {
        // asking retrofit to create webService object that implements what we
        // specified in the interface
        WebServiceAPI webServiceAPI = getRetrofit().create(WebServiceAPI.class);
        return webServiceAPI;
    }

}

