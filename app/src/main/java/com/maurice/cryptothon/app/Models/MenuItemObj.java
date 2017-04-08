package com.maurice.cryptothon.app.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuItemObj implements Comparable<MenuItemObj> {
    String TAG = "QUESTION.MODEL";
    public String name = "";
    public int val = 0;

    public MenuItemObj() {}

    //SERVER ENCODERS
    public static MenuItemObj decode(JSONObject obj){
        MenuItemObj re = new MenuItemObj();
        try {
            re.name = (obj.has("name")) ? obj.getString("name") : null;
            re.val = (obj.has("val")) ? obj.getInt("val") : 0;
        } catch (JSONException e) {e.printStackTrace();}
        return re;
    }

    public static ArrayList<MenuItemObj> decode(JSONArray obj){

        ArrayList<MenuItemObj> list = new ArrayList<>();
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
            jsonObject.put( "name", name );

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public int compareTo(MenuItemObj another) {
        return 0;//TODO : implement this
    }

}
