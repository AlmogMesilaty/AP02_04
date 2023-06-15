package com.example.ap02_04.api;

import com.example.ap02_04.entities.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {


    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    private User user;


    public UserAPI() {
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

    // get user
    public User getUser(String username) {

        Call<User> call = webServiceAPI.getUser(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        return user;
    }



    //@POST("Users")
    //Call<Void> addUser(@Body User user);

    //@GET("Users/{username}")
    //Call<User> getUser();


}
