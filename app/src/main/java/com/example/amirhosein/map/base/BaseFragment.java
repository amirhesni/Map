package com.example.amirhosein.map.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.ButterKnife;

/**
 * Created by ALI on 2017/05/26.
 */

public abstract class BaseFragment<PresenterT extends BasePresenter<ViewT>,
        ViewT extends BaseView> extends Fragment implements BaseView {

    private View view;
    private AppCompatActivity parentActivity;

    protected abstract int setLayoutId();

    protected abstract void initUi();

    protected PresenterT presenter;

    protected Bundle bundle;
    protected Gson gson;

    @Override
    public void onError(Throwable t) {

    }
    @Override
    public void onFailureReq() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(setLayoutId(), container, false);
        bundle = getArguments();
        gson = new Gson();
        if (getView() != null)
            ButterKnife.bind(this, getView());
        setPresenter();
        initUi();


        return view;
    }


    private void onBackPressed(TextView txt_back) {

    }


    @Nullable
    @Override
    public View getView() {
        return view;
    }


}
