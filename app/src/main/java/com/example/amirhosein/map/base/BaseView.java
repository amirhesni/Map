package com.example.amirhosein.map.base;

/**
 * Created by ALI on 2017/05/27.
 */

public interface BaseView{
    void setPresenter();
    void onError(Throwable t);
    void onFailureReq();

}
