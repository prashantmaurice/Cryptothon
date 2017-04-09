package com.maurice.cryptothon.app.Fragments.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.maurice.cryptothon.app.MainApplication;
import com.maurice.cryptothon.app.MasterActivity;
import com.maurice.cryptothon.app.Models.TransactionObj;
import com.maurice.cryptothon.app.R;
import com.maurice.cryptothon.app.Utils.Logg;
import com.maurice.cryptothon.app.Utils.NetworkCallback2;
import com.maurice.cryptothon.app.storage.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the main fragment user for listing user Notifications
 */
public class ProfileFragment extends android.support.v4.app.Fragment {
    String TAG = "OFFERSFRAG";
    public MasterActivity mActivity;
    ArrayList<TransactionObj> transactions = new ArrayList<>();
    ListView notificationsLV;
    TextView wallet;
    SwipeRefreshLayout refresh_cont;
    ProfileFragAdapter adapter;
    Data data;

    public ProfileFragment() {
        data = Data.getInstance(MainApplication.getInstance());
    }

    public static ProfileFragment newInstance(MasterActivity activityContext) {
        ProfileFragment myFragment = new ProfileFragment();
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
        rootView = inflater.inflate(R.layout.fragment_profile, null);

        notificationsLV = (ListView) rootView.findViewById(R.id.notificationsLV);
        adapter = new ProfileFragAdapter(getActivity(), transactions);
        notificationsLV.setAdapter(adapter);
        refresh_cont = (SwipeRefreshLayout) rootView.findViewById(R.id.refresh_cont);
        wallet = (TextView) rootView.findViewById(R.id.wallet);
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
        Data.getInstance(mActivity).getMyTransactions(new NetworkCallback2<List<TransactionObj>>() {
            @Override
            public void onSuccess(List<TransactionObj> objs) {
                Logg.d(TAG,"completeRefresh onSuccess");
                transactions.clear();
                transactions.addAll(objs);
                notifyDataSetChanged();
            }

            @Override
            public void onError() {
                Logg.d(TAG,"completeRefresh onError");
            }
        });
    }

    public double getAllCount(){
        double total = 0;
        for(int i=0;i<transactions.size();i++){
            total +=transactions.get(i).getValue();
        }
        return total;
    }

    public void notifyDataSetChanged() {
        transactions.clear();
        transactions.addAll(MainApplication.getInstance().data.transactions);
        adapter.notifyDataSetChanged();
        wallet.setText(""+getAllCount());
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

