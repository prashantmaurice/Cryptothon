package com.maurice.cryptothon.app.Fragments.Users;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.maurice.cryptothon.app.Models.UserObj;
import com.maurice.cryptothon.app.R;
import com.maurice.cryptothon.app.Views.UserViewBuilder;

import java.util.ArrayList;

/**
 * Created by maurice on 10/06/15.
 */
public class UsersFragAdapter extends ArrayAdapter<UserObj> {
    Activity mContext;
    private final ArrayList<UserObj> offers;

    @Override
    public int getCount() {
        return offers.size();
    }

    public UsersFragAdapter(Activity context, ArrayList<UserObj> offers){
        super(context, R.layout.smslist_list_item, offers);
        this.mContext = context;
        this.offers = offers;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = mContext.getLayoutInflater();
            view = UserViewBuilder.getJobCardView(mContext);
        } else {
            view = convertView;
        }

        UserViewBuilder.ViewHolder holder = (UserViewBuilder.ViewHolder) view.getTag();

        UserObj offer = offers.get(position);
        holder.inflateData(offer);
        return view;
    }

}
