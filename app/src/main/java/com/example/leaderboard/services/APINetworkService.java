package com.example.leaderboard.services;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APINetworkService {


    private static String BASE_URL =  "https://gadsapi.herokuapp.com/";

    private static Retrofit retrofit;

    public static Retrofit getConnection(){

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;

    }
}
