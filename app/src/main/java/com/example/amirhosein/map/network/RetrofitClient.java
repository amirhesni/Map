package com.example.amirhosein.map.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Android on 9/4/2017.
 */
public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }





}