package com.maurice.cryptothon.app.Views;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maurice.cryptothon.app.Models.TransactionObj;
import com.maurice.cryptothon.app.R;
import com.maurice.cryptothon.app.Utils.Logg;


/**
 * This is basic builder for getting a question view which can be used to inflate Question in Forum, ProfileScreen etc
 */
public class TransactionViewBuilder {
    static final String TAG = "TRANSACTION.VIEW";

    public static View getJobCardView(Activity activity){
        LayoutInflater inflator = LayoutInflater.from(activity);
        View mainView = inflator.inflate(R.layout.dialog_item_transaction, null);
        ViewHolder holder = new ViewHolder(mainView, activity);
        mainView.setTag(holder);
        return mainView;
                
    }

    public static class ViewHolder{
        public View mainView;
        public TextView tv_header, tv_subheader, tv_body,tv_cost;
        public ImageView iv_left;
        public TextView tv_account;
        public View leftcont;
        public TextView tv_line1,tv_line2,tv_line3,tv_acc_4;
        public Activity mContext;

        public ViewHolder(View view, Activity activity) {
            mContext = activity;
            mainView = view;
            tv_header = (TextView) view.findViewById(R.id.tv_header);
            tv_line1 = (TextView) view.findViewById(R.id.tv_line1);
            tv_line2 = (TextView) view.findViewById(R.id.tv_line2);
            tv_line3 = (TextView) view.findViewById(R.id.tv_line3);
        }


        // TODO - WTF! UserActivityObject and ActivityObject classes?! use ONE dude!

        public void inflateData(final TransactionObj restaurantObj){
            Logg.d(TAG, "Inflating data in Job view");
            tv_header.setText(restaurantObj.action);
            tv_line1.setText(""+restaurantObj.amount);
            tv_line2.setText(""+restaurantObj.status);
            tv_line3.setText(""+restaurantObj.date);

        }
    }
}
