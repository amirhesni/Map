package com.example.amirhosein.map.base;

import com.example.amirhosein.map.network.ApiUtils;
import com.example.amirhosein.map.network.RetrofitInterface;
import com.google.gson.Gson;


public abstract class BasePresenter<ViewT extends BaseView> {
    protected ViewT view;

    protected Gson gson;
    protected RetrofitInterface retrofitInterface;

    public BasePresenter(ViewT view) {
        this.view = view;
        gson = new Gson();
        retrofitInterface = ApiUtils.GetRetrofit();
    }


    public abstract void resume();


    public abstract void pause();


    public abstract void destroy();




}