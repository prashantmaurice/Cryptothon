package com.maurice.cryptothon.app.Dialogs.FeedbackDialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.maurice.cryptothon.app.Models.CouponObj;
import com.maurice.cryptothon.app.Models.QuestionObj;
import com.maurice.cryptothon.app.Models.RestaurantObj;
import com.maurice.cryptothon.app.R;
import com.maurice.cryptothon.app.Views.QuestionViewBuilder;

import java.util.List;

/**
 * Created by maurice on 10/06/15.
 */
public class FeedbackDialogAdapter extends ArrayAdapter<QuestionObj> {
    Activity mContext;
    private final List<QuestionObj> offers;
    RestaurantObj restaurantObj;
    CouponObj couponObj;

    @Override
    public int getCount() {
        return offers.size();
    }

    public FeedbackDialogAdapter(Activity context, RestaurantObj restaurantObj, CouponObj couponObj){
        super(context, R.layout.smslist_list_item, couponObj.questions);
        this.mContext = context;
        this.offers = couponObj.questions;
        this.couponObj = couponObj;
        this.restaurantObj = restaurantObj;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = mContext.getLayoutInflater();
            view = QuestionViewBuilder.getJobCardView(mContext);
        } else {
            view = convertView;
        }

        QuestionViewBuilder.ViewHolder holder = (QuestionViewBuilder.ViewHolder) view.getTag();
        QuestionObj offer = couponObj.questions.get(position);
        holder.inflateData(offer,restaurantObj);
        return view;
    }

}
