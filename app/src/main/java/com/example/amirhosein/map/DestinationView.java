package com.example.amirhosein.map;

import com.example.amirhosein.map.base.BaseView;
import com.example.amirhosein.map.model.AddressResponse;

/**
 * Created by amirhosein on 3/6/2019.
 */

interface DestinationView extends BaseView{
    void onSuccessGetAddress(AddressResponse body);
}
