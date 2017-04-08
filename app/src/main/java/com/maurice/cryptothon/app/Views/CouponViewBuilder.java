package com.maurice.cryptothon.app.Views;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maurice.cryptothon.app.Controllers.ToastMain;
import com.maurice.cryptothon.app.Models.CouponObj;
import com.maurice.cryptothon.app.Models.RestaurantObj;
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
        public TextView claim;
        public View leftcont;
        public TextView tv_acc_1,tv_acc_2,tv_acc_3,tv_acc_4;
        public Activity mContext;

        public ViewHolder(View view, Activity activity) {
            mContext = activity;
            mainView = view;
            tv_header = (TextView) view.findViewById(R.id.tv_header);
            tv_subheader = (TextView) view.findViewById(R.id.tv_subheader);
            claim = (TextView) view.findViewById(R.id.claim);
            leftcont = view.findViewById(R.id.leftcont);
        }


        // TODO - WTF! UserActivityObject and ActivityObject classes?! use ONE dude!

        public void inflateData(final CouponObj couponObj, final RestaurantObj restaurantObj){
            Logg.d(TAG, "Inflating data in Job view");
            tv_header.setText(couponObj.id);
            tv_subheader.setText(""+couponObj.name);
            refreshUI(couponObj);
            claim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (couponObj.claimed){
                        ToastMain.showSmartToast(mContext,"Already Claimed");
                    }else{
                        couponObj.claim(restaurantObj,new NetworkCallback() {
                            @Override
                            public void onSuccess() {
                                couponObj.claimed = true;
                                refreshUI(couponObj);
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

        public void refreshUI(CouponObj couponObj){
            leftcont.setBackgroundResource(!couponObj.claimed ? R.color.colorPrimary : R.color.colorSecondary);
            claim.setBackgroundResource(!couponObj.claimed ? R.color.colorPrimaryDark : R.color.colorSecondaryDark);
            claim.setText(!couponObj.claimed ? "Claim" : "Claimed");
        }



    }
}
