package com.maurice.cryptothon.app.Dialogs.MenuDialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.maurice.cryptothon.app.Models.MenuItemObj;
import com.maurice.cryptothon.app.Models.RestaurantObj;
import com.maurice.cryptothon.app.R;
import com.maurice.cryptothon.app.Views.MenuItemViewBuilder;

import java.util.List;

/**
 * Created by maurice on 10/06/15.
 */
public class MenuDialogAdapter extends ArrayAdapter<MenuItemObj> {
    Activity mContext;
    private final List<MenuItemObj> offers;
    RestaurantObj restaurantObj;

    @Override
    public int getCount() {
        return offers.size();
    }

    public MenuDialogAdapter(Activity context, RestaurantObj restaurantObj){
        super(context, R.layout.smslist_list_item, restaurantObj.menu);
        this.mContext = context;
        this.offers = restaurantObj.menu;
        this.restaurantObj = restaurantObj;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = mContext.getLayoutInflater();
            view = MenuItemViewBuilder.getJobCardView(mContext);
        } else {
            view = convertView;
        }

        MenuItemViewBuilder.ViewHolder holder = (MenuItemViewBuilder.ViewHolder) view.getTag();
        MenuItemObj offer = restaurantObj.menu.get(position);
        holder.inflateData(offer);
        return view;
    }

}
