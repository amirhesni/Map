package com.example.amirhosein.map;

import android.view.View;
import android.widget.TextView;

import com.example.amirhosein.map.base.BaseFragment;
import com.example.amirhosein.map.model.Address;
import com.example.amirhosein.map.model.AddressResponse;
import com.google.android.gms.maps.model.LatLng;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by amirhosein on 3/6/2019.
 */

public class DestinationFragment extends BaseFragment<DestinationPresenter, DestinationView> implements DestinationView {
    @BindView(R.id.confirm_destination_text_view)
    TextView confirmDestinationTextView;
    @BindView(R.id.origin_address)
    TextView originAddress;
    @BindView(R.id.origin_area)
    TextView originArea;

    @BindView(R.id.destination_address)
    TextView destinationAddress;
    @BindView(R.id.destination_area)
    TextView destinationArea;

    @BindView(R.id.destination_confrim_layout)
    View destinationConfrimLayout;

    AddressResponse addressResponse;

    @Override
    public void setPresenter() {
        presenter = new DestinationPresenter(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_destination;
    }

    @Override
    protected void initUi() {
        if (bundle != null)
            addressResponse = gson.fromJson(bundle.getString("originData"), AddressResponse.class);
        else
            addressResponse = new AddressResponse();
        originArea.setText(addressResponse.getAddress().getSuburb());
        originAddress.setText(addressResponse.getAddress().getRoad());
    }

    public void getAddress(LatLng target) {
        if (presenter!=null){
            presenter.getAddress(target);
            setDisable();
        }
    }

    @Override
    public void onSuccessGetAddress(AddressResponse addressResponse) {
        destinationAddress.setText(addressResponse.getAddress().getRoad());
        destinationArea.setText(addressResponse.getAddress().getSuburb());
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


    private void setEnable() {
        destinationArea.setVisibility(View.VISIBLE);
        destinationConfrimLayout.setEnabled(true);
        destinationConfrimLayout.setAlpha(1);
    }

    private void setDisable() {
        destinationAddress.setText("در حال بارگذاری ...");
        destinationArea.setVisibility(View.INVISIBLE);
        destinationConfrimLayout.setEnabled(false);
        destinationConfrimLayout.setAlpha(0.5f);
    }

    @OnClick({R.id.destination_confrim_layout, R.id.back_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.destination_confrim_layout:
                if (getActivity() != null)
                    ((MainActivity) getActivity()).setDestinationData(addressResponse);
                break;
            case R.id.back_btn:
                if (getActivity()!=null)
                    ((MainActivity) getActivity()).popFragment();
                break;
        }
    }
}
