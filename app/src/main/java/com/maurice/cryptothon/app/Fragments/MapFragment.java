package com.maurice.cryptothon.app.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.maurice.cryptothon.app.MainApplication;
import com.maurice.cryptothon.app.MasterActivity;
import com.maurice.cryptothon.app.Models.RestaurantObj;
import com.maurice.cryptothon.app.R;
import com.maurice.cryptothon.app.Utils.Logg;
import com.maurice.cryptothon.app.Utils.NetworkCallback2;
import com.maurice.cryptothon.app.storage.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maurice on 08/04/17.
 */

public class MapFragment extends Fragment {
    String TAG = "MAPFRAG";
    public MasterActivity mActivity;
    MapView mMapView;
    private GoogleMap googleMap;

    List<RestaurantObj> restaurants = new ArrayList<>();

    public MapFragment() {
    }

    public static MapFragment newInstance(MasterActivity activityContext) {
        MapFragment myFragment = new MapFragment();
        myFragment.mActivity = activityContext;
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_master, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(MainApplication.getInstance(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainApplication.getInstance(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
//                LatLng sydney = new LatLng(-34, 151);
//                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
//                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                completeRefresh();
            }
        });

        return rootView;
    }

    public void addMarkers(){
        if(googleMap!=null){
            googleMap.clear();
            for(int i = 0; i< restaurants.size(); i++){
                RestaurantObj rObj = restaurants.get(i);
                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(rObj.lat, rObj.lng))
                        .title(rObj.name));
            }

            if(restaurants.size()>0){
                zoomTo(restaurants.get(0).lat, restaurants.get(0).lng);
            }
        }
    }

    public void zoomTo(Double lat, Double lng){
        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(lat, lng)).zoom(15).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void completeRefresh(){
        Logg.d(TAG,"completeRefresh");
        Data.getInstance(mActivity).pullOffersFromServer(new NetworkCallback2<List<RestaurantObj>>() {
            @Override
            public void onSuccess(List<RestaurantObj> objs) {
                Logg.d(TAG,"completeRefresh onSuccess");
                restaurants.clear();
                restaurants.addAll(objs);
                addMarkers();
            }

            @Override
            public void onError() {
                Logg.d(TAG,"completeRefresh onError");
            }
        });
    }
}
