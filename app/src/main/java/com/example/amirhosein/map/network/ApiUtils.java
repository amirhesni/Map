package com.example.amirhosein.map.network;

/**
 * Created by amirhesni on 9/11/17.
 */

public class ApiUtils {

    public static final String BASE_URL = "https://nominatim.openstreetmap.org/";


    public static RetrofitInterface GetRetrofit() {
        return RetrofitClient.getClient(BASE_URL).create(RetrofitInterface.class);
    }


}
