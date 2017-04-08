package com.maurice.cryptothon.app.Fragments.Offers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
 * This is the main fragment user for listing user Notifications
 */
public class OffersFragment extends android.support.v4.app.Fragment {
    String TAG = "OFFERSFRAG";
    public MasterActivity mActivity;
    ArrayList<RestaurantObj> restaurents = new ArrayList<>();
    ListView notificationsLV;
    SwipeRefreshLayout refresh_cont;
    OffersFragAdapter adapter;
    Data data;

    public OffersFragment() {
        data = Data.getInstance(MainApplication.getInstance());
    }

    public static OffersFragment newInstance(MasterActivity activityContext) {
        OffersFragment myFragment = new OffersFragment();
        myFragment.mActivity = activityContext;
        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView;
        rootView = inflater.inflate(R.layout.fragment_notifications, null);

        notificationsLV = (ListView) rootView.findViewById(R.id.notificationsLV);
        adapter = new OffersFragAdapter(getActivity(), restaurents);
        notificationsLV.setAdapter(adapter);
        refresh_cont = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_cont);
        refresh_cont.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh_cont.setRefreshing(false);
                completeRefresh();
            }
        });
        completeRefresh();
        notifyDataSetChanged();
        return rootView;
    }


    public void completeRefresh(){
        Logg.d(TAG,"completeRefresh");
        Data.getInstance(mActivity).pullOffersFromServer(new NetworkCallback2<List<RestaurantObj>>() {
            @Override
            public void onSuccess(List<RestaurantObj> objs) {
                Logg.d(TAG,"completeRefresh onSuccess");
                restaurents.clear();
                restaurents.addAll(objs);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError() {
                Logg.d(TAG,"completeRefresh onError");
            }
        });
    }

    public void notifyDataSetChanged() {
        restaurents.clear();
        restaurents.addAll(MainApplication.getInstance().data.offers);
        adapter.notifyDataSetChanged();



    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        for(int i=0;i<menu.size();i++){
            menu.getItem(i).setVisible(false);
        }
    }

    @Override
    public void onDestroy() {
//        LocalBroadcastManager.getInstance(this).unregisterReceiver( notificationReceiver );
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
    }

}

