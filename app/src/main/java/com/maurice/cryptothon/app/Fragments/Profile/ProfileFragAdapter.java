package com.maurice.cryptothon.app.Fragments.Profile;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.maurice.cryptothon.app.Models.TransactionObj;
import com.maurice.cryptothon.app.R;
import com.maurice.cryptothon.app.Views.TransactionViewBuilder;

import java.util.ArrayList;

/**
 * Created by maurice on 10/06/15.
 */
public class ProfileFragAdapter extends ArrayAdapter<TransactionObj> {
    Activity mContext;
    private final ArrayList<TransactionObj> offers;

    @Override
    public int getCount() {
        return offers.size();
    }

    public ProfileFragAdapter(Activity context, ArrayList<TransactionObj> offers){
        super(context, R.layout.smslist_list_item, offers);
        this.mContext = context;
        this.offers = offers;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = mContext.getLayoutInflater();
            view = TransactionViewBuilder.getJobCardView(mContext);
        } else {
            view = convertView;
        }

        TransactionViewBuilder.ViewHolder holder = (TransactionViewBuilder.ViewHolder) view.getTag();

        TransactionObj offer = offers.get(position);
        holder.inflateData(offer);
        return view;
    }

}
