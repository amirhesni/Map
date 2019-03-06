package com.example.amirhosein.map.network;

import com.example.amirhosein.map.model.AddressResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Android on 9/4/2017.
 */

public interface RetrofitInterface {


    @GET("reverse")
    Call<AddressResponse> getAddress(@QueryMap HashMap<String , Object> query);

//    @Query("format") String format, @Query("lat") double lat , @Query("lon") double lon
}