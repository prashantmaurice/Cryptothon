package com.maurice.cryptothon.app.Dialogs.FeedbackDialog;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.maurice.cryptothon.app.Controllers.ToastMain;
import com.maurice.cryptothon.app.MainApplication;
import com.maurice.cryptothon.app.Models.CouponObj;
import com.maurice.cryptothon.app.Models.RestaurantObj;
import com.maurice.cryptothon.app.R;
import com.maurice.cryptothon.app.Utils.NetworkCallback;
import com.maurice.cryptothon.app.Utils.SuccessCallback;
import com.maurice.cryptothon.app.storage.Data;

/**
 * Created by maurice on 08/04/17.
 */

public class FeedbackDialog {

    static public void show(final Activity activity, final RestaurantObj rObj, final CouponObj couponObj, final SuccessCallback callback){
//        Dialog dialog = new Dialog(activity);
//        dialog.setContentView(R.layout.dialog_restaurant);
//        ListView lv = (ListView) dialog.findViewById(R.id.lv);
//        dialog.setCancelable(true);
//        dialog.setTitle("ListView");
//        dialog.show();


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View convertView = inflater.inflate(R.layout.dialog_feedback, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle(rObj.name);
        ListView lv = (ListView) convertView.findViewById(R.id.lv);
        TextView submit = (TextView) convertView.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data.getInstance(MainApplication.getInstance()).submitFeedback(rObj, couponObj, new NetworkCallback() {
                    @Override
                    public void onSuccess() {
                        couponObj.claimed = true;
                        ToastMain.showSmartToast(activity,"Successfully Claimed Feedback");
                        callback.onSuccess();
                    }

                    @Override
                    public void onError() {
                        ToastMain.showSmartToast(activity,"Error in Claiming Feedback");
                    }
                });
            }
        });
        FeedbackDialogAdapter adapter = new FeedbackDialogAdapter(activity,rObj, couponObj);
        lv.setAdapter(adapter);
        alertDialog.show();
    }
}
