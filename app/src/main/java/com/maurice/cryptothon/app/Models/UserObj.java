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
import java.util.List;

import static android.R.attr.name;

public class UserObj implements Comparable<UserObj> {
    String TAG = "COUPON.MODEL";
    public String id = "";
    public String clientName = "";
    public String description = "";
    public String type = "checkin";//"checkin", "feedback"
    public double lat,lng,value;
    public String address;
    public Boolean claimed;
    public int questionsAnswered;
    public List<QuestionObj> questions = new ArrayList<>();


    public UserObj() {}

    //SERVER ENCODERS
    public static UserObj decode(JSONObject obj){
        UserObj re = new UserObj();
        try {
            re.id = (obj.has("id")) ? obj.getString("id") : null;
            re.clientName = (obj.has("clientName")) ? obj.getString("clientName") : "No name";
            re.description = (obj.has("description")) ? obj.getString("description") : "No description";
            re.type = (obj.has("type")) ? obj.getString("type") : "checkin";
            re.lat = (obj.has("lat")) ? obj.getDouble("lat") : 0;
            re.lng = (obj.has("lng")) ? obj.getDouble("lng") : 0;
            re.value = (obj.has("value")) ? obj.getDouble("value") : 0;
            re.questionsAnswered = (obj.has("questionsAnswered")) ? obj.getInt("questionsAnswered") : 0;
            re.claimed = (obj.has("claimed")) ? obj.getBoolean("claimed") : false;
            re.questions = (obj.has("questions")) ? QuestionObj.decode(obj.getJSONArray("questions")) : new ArrayList<QuestionObj>();
        } catch (JSONException e) {e.printStackTrace();}
        return re;
    }

    public static ArrayList<UserObj> decode(JSONArray obj){

        ArrayList<UserObj> list = new ArrayList<>();
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
            jsonObject.put( "name", clientName );
            jsonObject.put( "lat", lat );
            jsonObject.put( "lng", lng );
            jsonObject.put( "value", value );

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public int compareTo(UserObj another) {
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

    public int numberOfQuestionsAnswered(){
        int count = 0;
        for(int i=0;i<questions.size();i++){
            if(questions.get(i).rating!=0) count++;
        }
        return questionsAnswered + count;
    }

}
