package com.maurice.cryptothon.app.Fragments.Claims;

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
import com.maurice.cryptothon.app.Models.UserObj;
import com.maurice.cryptothon.app.R;
import com.maurice.cryptothon.app.Utils.Logg;
import com.maurice.cryptothon.app.Utils.NetworkCallback2;
import com.maurice.cryptothon.app.storage.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the main fragment user for listing user Notifications
 */
public class ClaimsFragment extends android.support.v4.app.Fragment {
    String TAG = "OFFERSFRAG";
    public MasterActivity mActivity;
    ArrayList<UserObj> restaurants = new ArrayList<>();
    ListView notificationsLV;
    SwipeRefreshLayout refresh_cont;
    ClaimsFragAdapter adapter;
    Data data;

    public static ClaimsFragment instance;

    public ClaimsFragment() {
        data = Data.getInstance(MainApplication.getInstance());
    }

    public static ClaimsFragment newInstance(MasterActivity activityContext) {
        ClaimsFragment myFragment = new ClaimsFragment();
        myFragment.mActivity = activityContext;
        instance = myFragment;
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
        adapter = new ClaimsFragAdapter(getActivity(), restaurants);
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
        Data.getInstance(mActivity).getClaims(new NetworkCallback2<List<UserObj>>() {
            @Override
            public void onSuccess(List<UserObj> objs) {
                Logg.d(TAG,"completeRefresh onSuccess");
                restaurants.clear();
                restaurants.addAll(objs);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError() {
                Logg.d(TAG,"completeRefresh onError");
            }
        });
    }

    public void notifyDataSetChanged() {
        restaurants.clear();
        restaurants.addAll(MainApplication.getInstance().data.users);
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

