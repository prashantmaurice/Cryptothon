package com.maurice.cryptothon.app.Fragments.Offers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.maurice.cryptothon.app.Models.RestaurantObj;
import com.maurice.cryptothon.app.R;
import com.maurice.cryptothon.app.Views.RestaurantViewBuilder;

import java.util.ArrayList;

/**
 * Created by maurice on 10/06/15.
 */
public class RestaurantsFragAdapter extends ArrayAdapter<RestaurantObj> {
    Activity mContext;
    private final ArrayList<RestaurantObj> offers;

    @Override
    public int getCount() {
        return offers.size();
    }

    public RestaurantsFragAdapter(Activity context, ArrayList<RestaurantObj> offers){
        super(context, R.layout.smslist_list_item, offers);
        this.mContext = context;
        this.offers = offers;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = mContext.getLayoutInflater();
            view = RestaurantViewBuilder.getJobCardView(mContext);
        } else {
            view = convertView;
        }

        RestaurantViewBuilder.ViewHolder holder = (RestaurantViewBuilder.ViewHolder) view.getTag();

        RestaurantObj offer = offers.get(position);
        holder.inflateData(offer);
        return view;
    }

}
