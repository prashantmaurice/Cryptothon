package com.maurice.cryptothon.app.Views;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maurice.cryptothon.app.Controllers.ToastMain;
import com.maurice.cryptothon.app.Models.CouponObj;
import com.maurice.cryptothon.app.R;
import com.maurice.cryptothon.app.Utils.Logg;
import com.maurice.cryptothon.app.Utils.NetworkCallback;


/**
 * This is basic builder for getting a question view which can be used to inflate Question in Forum, ProfileScreen etc
 */
public class CouponViewBuilder {
    static final String TAG = "JOBCARDVIEW";

    public static View getJobCardView(Activity activity){
        LayoutInflater inflator = LayoutInflater.from(activity);
        View mainView = inflator.inflate(R.layout.dialog_item_coupon, null);
        ViewHolder holder = new ViewHolder(mainView, activity);
        mainView.setTag(holder);
        return mainView;
                
    }

    public static class ViewHolder{
        public View mainView;
        public TextView tv_header, tv_subheader, tv_body,tv_cost;
        public ImageView iv_left;
        public View claim;
        public TextView tv_acc_1,tv_acc_2,tv_acc_3,tv_acc_4;
        public Activity mContext;

        public ViewHolder(View view, Activity activity) {
            mContext = activity;
            mainView = view;
            tv_header = (TextView) view.findViewById(R.id.tv_header);
            tv_subheader = (TextView) view.findViewById(R.id.tv_subheader);
            claim = view.findViewById(R.id.claim);
        }


        // TODO - WTF! UserActivityObject and ActivityObject classes?! use ONE dude!

        public void inflateData(final CouponObj msg){
            Logg.d(TAG, "Inflating data in Job view");
            tv_header.setText(msg.id);
            tv_subheader.setText(""+msg.name);
            mainView.setEnabled(!msg.claimed);
            claim.setBackgroundResource(!msg.claimed ? R.color.colorPrimaryDark : R.color.colorSecondary);
            claim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (msg.claimed){
                        ToastMain.showSmartToast(mContext,"Already Claimed");
                    }else{
                        msg.claim(new NetworkCallback() {
                            @Override
                            public void onSuccess() {
                                ToastMain.showSmartToast(mContext,"Successfully Claimed");
                            }

                            @Override
                            public void onError() {
                                ToastMain.showSmartToast(mContext,"Error in Claiming Coupon");
                            }
                        });
                    }


                }
            });

        }



    }
}
