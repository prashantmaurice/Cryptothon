package com.maurice.cryptothon.app.Views;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.maurice.cryptothon.app.Models.UserObj;
import com.maurice.cryptothon.app.R;
import com.maurice.cryptothon.app.Utils.Logg;


/**
 * This is basic builder for getting a question view which can be used to inflate Question in Forum, ProfileScreen etc
 */
public class UserViewBuilder {
    static final String TAG = "JOBCARDVIEW";

    public static View getJobCardView(Activity activity){
        LayoutInflater inflator = LayoutInflater.from(activity);
        View mainView = inflator.inflate(R.layout.mycards_list_item, null);
        ViewHolder holder = new ViewHolder(mainView, activity);
        mainView.setTag(holder);
        return mainView;
                
    }

    public static class ViewHolder{
        public View mainView;
        public TextView tv_header, tv_subheader, tv_body,tv_cost;
        public View menu;
        public TextView tv_acc_1,tv_acc_2,tv_acc_3,tv_acc_4;
        public Activity mContext;

        public ViewHolder(View view, Activity activity) {
            mContext = activity;
            mainView = view;
            menu = view.findViewById(R.id.menu);
            tv_header = (TextView) view.findViewById(R.id.tv_header);
            tv_subheader = (TextView) view.findViewById(R.id.tv_subheader);
        }


        // TODO - WTF! UserActivityObject and ActivityObject classes?! use ONE dude!

        public void inflateData(final UserObj msg){
            Logg.d(TAG, "Inflating data in Job view");
            tv_header.setText(msg.clientName);
//            tv_subheader.setText(""+msg.numberClaimed()+"/"+msg.couponObjs.size() + " coupons claimed");
//            mainView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    RestaurantDialog.show(mContext,msg);
//                }
//            });
//            menu.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    MenuDialog.show(mContext, msg, new SuccessCallback() {
//                        @Override
//                        public void onSuccess() {
//
//                        }
//                    });
//                }
//            });

        }



    }
}
