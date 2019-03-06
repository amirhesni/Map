package com.example.amirhosein.map;

import android.view.View;
import android.widget.TextView;

import com.example.amirhosein.map.base.BaseFragment;
import com.example.amirhosein.map.model.AddressResponse;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by amirhosein on 3/5/2019.
 */

public class OriginFragment extends BaseFragment<OriginPresenter, OriginView> implements OriginView {
    @BindView(R.id.confirm_origin_text_view)
    TextView confirmOriginTextView;
    @BindView(R.id.origin_address)
    TextView originAddress;
    @BindView(R.id.origin_area)
    TextView originArea;
    @BindView(R.id.origin_confrim_layout)
    View originConfrimLayout;

    AddressResponse addressResponse;
    @Override
    public void setPresenter() {
        presenter = new OriginPresenter(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_confirm;
    }

    @Override
    protected void initUi() {

    }

    public void getAddress(LatLng latLng) {
        if (presenter != null) {
            presenter.getAddress(latLng);
            setDisable();
        }
    }

    private void setEnable() {
        originArea.setVisibility(View.VISIBLE);
        originConfrimLayout.setEnabled(true);
        originConfrimLayout.setAlpha(1);
    }

    private void setDisable() {
        originAddress.setText("در حال بارگذاری ...");
        originArea.setVisibility(View.INVISIBLE);
        originConfrimLayout.setEnabled(false);
        originConfrimLayout.setAlpha(0.5f);
    }

    @Override
    public void onSuccessGetAddress(AddressResponse addressResponse) {
        originAddress.setText(addressResponse.getAddress().getRoad());
        originArea.setText(addressResponse.getAddress().getSuburb());
        this.addressResponse = addressResponse;
        setEnable();
    }

    @Override
    public void onFailureReq() {
        super.onFailureReq();
        setEnable();
        //TODO show error to user
    }

    @Override
    public void onError(Throwable t) {
        super.onError(t);
        setEnable();
        //TODO show error to user
    }

    @OnClick(R.id.origin_confrim_layout)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.origin_confrim_layout:
                if (getActivity() != null)
                    ((MainActivity) getActivity()).setOriginData(addressResponse);
                break;
        }
    }
}
