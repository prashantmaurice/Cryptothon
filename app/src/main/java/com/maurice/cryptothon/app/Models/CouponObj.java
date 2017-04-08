package com.maurice.cryptothon.app.Models;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.maurice.cryptothon.app.MainApplication;
import com.maurice.cryptothon.app.Utils.Logg;
import com.maurice.cryptothon.app.Utils.NetworkCallback;
import com.maurice.cryptothon.app.Utils.Router;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CouponObj implements Comparable<CouponObj> {
    String TAG = "COUPON.MODEL";
    public String id = "";
    public String name = "";

    public double lat,lng;
    public String address;
    public Boolean claimed;

    public CouponObj() {}

    //SERVER ENCODERS
    public static CouponObj decode(JSONObject obj){
        CouponObj re = new CouponObj();
        try {
            re.id = (obj.has("id")) ? obj.getString("id") : null;
            re.name = (obj.has("name")) ? obj.getString("name") : "No name";
            re.lat = (obj.has("lat")) ? obj.getDouble("lat") : 0;
            re.lng = (obj.has("lng")) ? obj.getDouble("lng") : 0;
            re.claimed = (obj.has("claimed")) ? obj.getBoolean("claimed") : false;

        } catch (JSONException e) {e.printStackTrace();}
        return re;
    }

    public static ArrayList<CouponObj> decode(JSONArray obj){

        ArrayList<CouponObj> list = new ArrayList<>();
        for(int i=0;i<obj.length();i++){
            try {
                list.add(decode(obj.getJSONObject(i)));
            } catch (JSONException e) {e.printStackTrace();}
        }
        return list;
    }

    public JSONObject encode(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put( "id", id );
            jsonObject.put( "name", name );
            jsonObject.put( "lat", lat );
            jsonObject.put( "lng", lng );

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public int compareTo(CouponObj another) {
        return 0;//TODO : implement this
    }

    public void claim(RestaurantObj restaurantObj, final NetworkCallback callback){
        String url = Router.Restaurants.claim();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",restaurantObj.id);
        } catch (JSONException e) {e.printStackTrace();}

        MainApplication.getInstance().addRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Logg.d(TAG, "USER DATA : " + jsonObject.toString());
                try {
//                    JSONObject result = jsonObject.getJSONObject("data");
//                    JSONArray offersJSON = result.getJSONArray("restaurents");
//                    offers.clear();
//                    offers.addAll(decode(offersJSON));
                    Boolean success = jsonObject.getBoolean("success");
                    if(success){
                        if(callback!=null) callback.onSuccess();
                    }else{
                        if(callback!=null) callback.onError();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Logg.e(TAG, "ERROR : " + volleyError);
                if(callback!=null) callback.onError();
            }
        });
    }

}
