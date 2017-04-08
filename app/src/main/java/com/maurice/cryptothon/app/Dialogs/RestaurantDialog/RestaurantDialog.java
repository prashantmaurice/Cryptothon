package com.maurice.cryptothon.app.Dialogs.RestaurantDialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.maurice.cryptothon.app.Models.RestaurantObj;
import com.maurice.cryptothon.app.R;

/**
 * Created by maurice on 08/04/17.
 */

public class RestaurantDialog {

    public static AlertDialog dialog;

    static public void show(Activity activity, RestaurantObj rObj){
//        Dialog dialog = new Dialog(activity);
//        dialog.setContentView(R.layout.dialog_restaurant);
//        ListView lv = (ListView) dialog.findViewById(R.id.lv);
//        dialog.setCancelable(true);
//        dialog.setTitle("ListView");
//        dialog.show();


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View convertView = inflater.inflate(R.layout.dialog_restaurant, null);
        alertDialog.setView(convertView);
        alertDialog.setTitle(rObj.name);
        ListView lv = (ListView) convertView.findViewById(R.id.lv);
        RestautantDialogAdapter adapter = new RestautantDialogAdapter(activity,rObj);
        lv.setAdapter(adapter);
        dialog = alertDialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialog = null;
            }
        });
    }
}
