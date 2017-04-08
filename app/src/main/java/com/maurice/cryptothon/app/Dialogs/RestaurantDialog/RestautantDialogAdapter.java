package com.maurice.cryptothon.app.Dialogs.RestaurantDialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.maurice.cryptothon.app.Models.CouponObj;
import com.maurice.cryptothon.app.Models.RestaurantObj;
import com.maurice.cryptothon.app.R;
import com.maurice.cryptothon.app.Views.CouponViewBuilder;

import java.util.List;

/**
 * Created by maurice on 10/06/15.
 */
public class RestautantDialogAdapter extends ArrayAdapter<CouponObj> {
    Activity mContext;
    private final List<CouponObj> offers;
    RestaurantObj restaurantObj;

    @Override
    public int getCount() {
        return offers.size();
    }

    public RestautantDialogAdapter(Activity context, RestaurantObj restaurantObj){
        super(context, R.layout.smslist_list_item, restaurantObj.couponObjs);
        this.mContext = context;
        this.offers = restaurantObj.couponObjs;
        this.restaurantObj = restaurantObj;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = mContext.getLayoutInflater();
            view = CouponViewBuilder.getJobCardView(mContext);
        } else {
            view = convertView;
        }

        CouponViewBuilder.ViewHolder holder = (CouponViewBuilder.ViewHolder) view.getTag();

        CouponObj offer = restaurantObj.couponObjs.get(position);
        holder.inflateData(offer,restaurantObj);
        return view;
    }

}
