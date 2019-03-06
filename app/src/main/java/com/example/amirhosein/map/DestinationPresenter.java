package com.example.amirhosein.map;

import android.util.Log;

import com.example.amirhosein.map.base.BasePresenter;
import com.example.amirhosein.map.model.AddressResponse;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by amirhosein on 3/6/2019.
 */

public class DestinationPresenter extends BasePresenter<DestinationView> {
    Call<AddressResponse> call;

    public DestinationPresenter(DestinationView view) {
        super(view);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    public void getAddress(LatLng latLng) {

        final HashMap<String, Object> queryMap = new HashMap<>();
        queryMap.put("format", "jsonv2");
        queryMap.put("lat", latLng.latitude);
        queryMap.put("lon", latLng.longitude);
        checkIfIsCompilted();
        call = retrofitInterface.getAddress(queryMap);
        call.enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                if (response.isSuccessful()) {
                    view.onSuccessGetAddress(response.body());
                } else {
                    view.onFailureReq();
                }
            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {
                view.onError(t);
            }
        });
    }

    private void checkIfIsCompilted() {
        if (call!=null && !call.isExecuted()){
            call.cancel();
        }
    }
}
