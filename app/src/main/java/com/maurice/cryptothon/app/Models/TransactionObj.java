package com.maurice.cryptothon.app.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TransactionObj implements Comparable<TransactionObj> {
    String TAG = "TRANSACTION.MODEL";
    public String id = "";
    public String fromTo = "";
    public String name, amount, status, date, action;
    public String description = "";
    public String type = "checkin";//"checkin", "feedback"
    public double lat,lng,value;
    public String address;
    public Boolean claimed;
    public int questionsAnswered;
    public List<QuestionObj> questions = new ArrayList<>();

    public TransactionObj() {}

    //SERVER ENCODERS
    public static TransactionObj decode(JSONObject obj){
        TransactionObj re = new TransactionObj();
        try {
            re.id = (obj.has("id")) ? obj.getString("id") : null;
            re.fromTo = (obj.has("from/to")) ? obj.getString("from/to") : "";
            re.amount = (obj.has("amount")) ? obj.getString("amount") : "No data";
            re.action = (obj.has("action")) ? obj.getString("action") : "No data";
            re.status = (obj.has("status")) ? obj.getString("status") : "No data";
            re.date = (obj.has("date")) ? obj.getString("date") : "No date";
        } catch (JSONException e) {e.printStackTrace();}
        return re;
    }
    public double getValue(){
        try {
            return Double.parseDouble(amount);
        }catch (NumberFormatException e){
            return 0;
        }
    }

    public static ArrayList<TransactionObj> decode(JSONArray obj){

        ArrayList<TransactionObj> list = new ArrayList<>();
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
            jsonObject.put( "value", value );

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public int compareTo(TransactionObj another) {
        return 0;//TODO : implement this
    }


}
