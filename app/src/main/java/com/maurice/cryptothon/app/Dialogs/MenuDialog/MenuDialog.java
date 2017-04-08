package com.maurice.cryptothon.app.Dialogs.MenuDialog;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.maurice.cryptothon.app.Models.RestaurantObj;
import com.maurice.cryptothon.app.R;
import com.maurice.cryptothon.app.Utils.SuccessCallback;

/**
 * Created by maurice on 08/04/17.
 */

public class MenuDialog {

    static public AlertDialog show(final Activity activity, final RestaurantObj rObj, final SuccessCallback callback){
//        Dialog dialog = new Dialog(activity);
//        dialog.setContentView(R.layout.dialog_restaurant);
//        ListView lv = (ListView) dialog.findViewById(R.id.lv);
//        dialog.setCancelable(true);
//        dialog.setTitle("ListView");
//        dialog.show();


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View convertView = inflater.inflate(R.layout.dialog_menu, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle(rObj.name+"'s menu");
        ListView lv = (ListView) convertView.findViewById(R.id.lv);
//        TextView submit = (TextView) convertView.findViewById(R.id.submit);
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Data.getInstance(MainApplication.getInstance()).submitFeedback(rObj, couponObj, new NetworkCallback() {
//                    @Override
//                    public void onSuccess() {
//                        couponObj.claimed = true;
//                        ToastMain.showSmartToast(activity,"Successfully Claimed Feedback");
//                        callback.onSuccess();
//                    }
//
//                    @Override
//                    public void onError() {
//                        ToastMain.showSmartToast(activity,"Error in Claiming Feedback");
//                    }
//                });
//            }
//        });
        MenuDialogAdapter adapter = new MenuDialogAdapter(activity,rObj);
        lv.setAdapter(adapter);
        return alertDialog.show();
    }
}
