package com.example.amirhosein.map;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.amirhosein.map.model.AddressResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity
        implements GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;
    private LatLng myLatLng;
    private Boolean myLocationReaded = false;

    //    private Marker myLocationMarker;
    private boolean setMylocationMarker = false;


    OriginFragment originFragment;
    DestinationFragment destinationFragment;

    public static final int ACCESS_FINE_LOCATION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION);

            }
        }
        setUpMapIfNeeded();
        originFragment = new OriginFragment();
        addFragment(originFragment, "");
    }


    protected FragmentManager fm;
    protected FragmentTransaction ft;

    public void addFragment(final Fragment fragment, final String tag) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ft.replace(R.id.fragment_container, fragment, tag);
                ft.addToBackStack(tag);
                ft.commitAllowingStateLoss();
            }
        };
        handler.postDelayed(runnable, 100);
    }

    public void addFragment(final Fragment fragment, final String tag, Bundle bundle) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        fragment.setArguments(bundle);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ft.replace(R.id.fragment_container, fragment, tag);
                ft.addToBackStack(tag);
                ft.commitAllowingStateLoss();
            }
        };
        handler.postDelayed(runnable, 100);
    }

    boolean originMode = true;

    public void setOriginData(AddressResponse addressResponse) {
        originMode = false;
        final BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker);
        destinationFragment = new DestinationFragment();
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.valueOf(addressResponse.getLat()), Double.valueOf(addressResponse.getLon())))
                .title("Origin Location")
                .icon(icon));
        focusOnMyLocation();
        Bundle bundle = new Bundle();
        bundle.putString("originData", new Gson().toJson(addressResponse));
        addFragment(destinationFragment, "destination", bundle);

    }

    public void setDestinationData(AddressResponse addressResponse) {
        final BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker);
        destinationFragment = new DestinationFragment();
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.valueOf(addressResponse.getLat()), Double.valueOf(addressResponse.getLon())))
                .title("Origin Location")
                .icon(icon));
        focusOnMyLocation();
    }

    public void popFragment() {

        if (fm.getBackStackEntryCount() == 0) {
            finish();
        } else {
            fm = getSupportFragmentManager();
            fm.popBackStack();
            originMode = true;
            mMap.clear();
            originFragment.getAddress(mMap.getCameraPosition().target);
        }

    }


    public interface OnMyLocationMarkerClick {

        void onClick();

    }


    OnMyLocationMarkerClick onMyLocationMarkerClick;


    @Override
    public void onMapClick(LatLng latLng) {
        /*final BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker);
        if (myLocationMarker != null) {
            myLocationMarker.remove();
            myLocationMarker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("My Location")
                    .icon(icon));



        }*/
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17), 1000, null);

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        /*if (marker.equals(myLocationMarker)) {
            if (onMyLocationMarkerClick != null)
                onMyLocationMarkerClick.onClick();
            return false;
        }*/

        return false;
    }


    public void setUpMapIfNeeded() {
        if (mMap == null) {
            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;
                    setUpMap();
                }
            });
        }
    }

    private void setUpMap() {
        if (mMap != null) {


            mMap.setOnMapClickListener(this);

            mMap.setOnMarkerClickListener(this);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


                return;
            }
            mMap.setMyLocationEnabled(true);


//            final BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.marker);

            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {
                    if (!myLocationReaded) {
                        myLocationReaded = true;
                        if (!setMylocationMarker) {
                            myLatLng = new LatLng(location.getLatitude(),
                                    location.getLongitude());

                            /*myLocationMarker = mMap.addMarker(new MarkerOptions()
                                    .position(myLatLng)
                                    .title("My Location")
                                    .icon(icon));*/
                            mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                                @Override
                                public void onCameraIdle() {
                                    if (originMode)
                                        originFragment.getAddress(mMap.getCameraPosition().target);
                                    else
                                        destinationFragment.getAddress(mMap.getCameraPosition().target);
                                }
                            });
                            focusOnMyLocation();

                        } else {
                            setMyLocationMarker();
                        }
                    }

                }
            });
        }

    }


    private void setMyLocationMarker() {
        /*if (myLocationMarker != null) {
            myLocationMarker.remove();
        }*/
        /*myLocationMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(myLatitude, myLongitude))
                .title("My Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLatitude, myLongitude), 17), 1000, null);*/

    }

    public void focusOnMyLocation() {

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 17), 1000, null);
    }

}
