package com.maurice.cryptothon.app.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RestaurantObj implements Comparable<RestaurantObj> {

    public String id = "";
    public String name = "";
    public double lat,lng;
    public String address;
    public List<CouponObj> couponObjs = new ArrayList<>();
    public List<MenuItemObj> menu = new ArrayList<>();


    public RestaurantObj() {}

    //SERVER ENCODERS
    public static RestaurantObj decode(JSONObject obj){
        RestaurantObj re = new RestaurantObj();
        try {
            re.id = (obj.has("id")) ? obj.getString("id") : null;
            re.name = (obj.has("name")) ? obj.getString("name") : "No name";

            re.lat = (obj.has("lat")) ? obj.getDouble("lat") : 0;
            re.lng = (obj.has("lng")) ? obj.getDouble("lng") : 0;
            re.lng = (obj.has("lng")) ? obj.getDouble("lng") : 0;
            re.couponObjs = (obj.has("coupons")) ? CouponObj.decode(obj.getJSONArray("coupons")) : new ArrayList<CouponObj>();
            re.menu = (obj.has("menu")) ? MenuItemObj.decode(obj.getJSONArray("menu")) : new ArrayList<MenuItemObj>();
        } catch (JSONException e) {e.printStackTrace();}
        return re;
    }

    public static ArrayList<RestaurantObj> decode(JSONArray obj){

        ArrayList<RestaurantObj> list = new ArrayList<>();
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
    public int compareTo(RestaurantObj another) {
        return 0;//TODO : implement this
    }

    public int numberClaimed(){
        int claimed = 0;
        for(int i=0;i<couponObjs.size();i++){
            if (couponObjs.get(i).claimed) claimed++;
        }
        return claimed;
    }

}
