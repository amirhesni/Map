package com.example.amirhosein.map;

import com.example.amirhosein.map.base.BaseView;
import com.example.amirhosein.map.model.AddressResponse;

/**
 * Created by amirhosein on 3/5/2019.
 */

public interface OriginView extends BaseView{
    void onSuccessGetAddress(AddressResponse body);
}
