package com.maurice.cryptothon.app.storage;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.maurice.cryptothon.app.Controllers.LocalBroadcastHandler;
import com.maurice.cryptothon.app.MainApplication;
import com.maurice.cryptothon.app.Models.CouponObj;
import com.maurice.cryptothon.app.Models.RestaurantObj;
import com.maurice.cryptothon.app.Models.TransactionObj;
import com.maurice.cryptothon.app.Models.UserMain;
import com.maurice.cryptothon.app.Models.UserObj;
import com.maurice.cryptothon.app.Utils.Logg;
import com.maurice.cryptothon.app.Utils.NetworkCallback;
import com.maurice.cryptothon.app.Utils.NetworkCallback2;
import com.maurice.cryptothon.app.Utils.Router;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.maurice.cryptothon.app.Models.RestaurantObj.decode;

/**
 *  Instance of Data object contains all access to the complete date model underneath.
 *  Just pull the Data instance in your screen and make modifications to exposed objects
 *  underneath as you wish. Make sure you commit to changes after making changes in order
 *  to take effect in Server and LocalDb
 *
 *  @author maurice
 */


public class Data {
    String TAG = "DATA";
    private static Data instance;
    public UserMain userMain;
    Context mContext;

    public Map<String, Long> zoneTimes = new HashMap<>();
    public Set<String> proximityIds = new HashSet<>();
    public ArrayList<TransactionObj> transactions = new ArrayList<>();
    public ArrayList<UserObj> users = new ArrayList<>();
    public ArrayList<RestaurantObj> offers = new ArrayList<>();
    ArrayList<RestaurantObj> done = new ArrayList<>();

    private Data(Context context) {
        mContext = context;
        userMain = UserMain.getInstance(context);

        completePullFromServer();
    }

    public void completePullFromServer() {
        pullOffersFromServer(null);
//        pullCompletedFromServer(null);
    }

    //use this to retreive an instance of Data
    public static Data getInstance(Context context) {
        if(instance == null) instance = new Data(context);
        return instance;
    }

    //Refill function to generate all previous data
    public void refillCompleteData(JSONObject response){
        userMain.decodeFromServer(response);
        userMain.saveUserDataLocally();
    };

    public void pullOffersFromServer(final NetworkCallback2<List<RestaurantObj>> callback){
        String url = Router.Restaurants.all();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("lat",12.9285516);
            jsonObject.put("long",77.6135156);
        } catch (JSONException e) {e.printStackTrace();}

        MainApplication.getInstance().addRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Logg.d(TAG, "USER DATA : " + jsonObject.toString());
                try {
                    JSONObject result = jsonObject.getJSONObject("data");
                    JSONArray offersJSON = result.getJSONArray("restaurants");
                    offers.clear();
                    offers.addAll(decode(offersJSON));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(callback!=null) callback.onSuccess(offers);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Logg.e(TAG, "ERROR : " + volleyError);
                if(callback!=null) callback.onError();
            }
        });
    }

    public void pullCompletedFromServer(final NetworkCallback callback){
        String url = Router.Restaurants.completed();
        JSONObject jsonObject = new JSONObject();
        Logg.m("MAIN", "Pulling offers data from server : ");

        MainApplication.getInstance().addRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Logg.d(TAG, "USER DATA : " + jsonObject.toString());
                try {
                    JSONObject result = jsonObject.getJSONObject("data");
                    JSONArray offersJSON = result.getJSONArray("done");
                    done.clear();
                    done.addAll(decode(offersJSON));

                    LocalBroadcastHandler.sendBroadcast(mContext, LocalBroadcastHandler.DONE_UPDATED);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(callback!=null) callback.onSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Logg.e(TAG, "ERROR : " + volleyError);
                if(callback!=null) callback.onError();
            }
        });
    }


    public void pullRestaurant(String id, final NetworkCallback2<RestaurantObj> callback){
        String url = Router.Restaurants.one(id);
        JSONObject jsonObject = new JSONObject();
        MainApplication.getInstance().addRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Logg.d(TAG, "USER DATA : " + jsonObject.toString());
                try {
                    JSONObject result = jsonObject.getJSONObject("data");
                    RestaurantObj rObj = RestaurantObj.decode(result.getJSONObject("restaurant"));
                    LocalBroadcastHandler.sendBroadcast(mContext, LocalBroadcastHandler.DONE_UPDATED);
                    if(callback!=null) callback.onSuccess(rObj);
                } catch (JSONException e) {
                    e.printStackTrace();
                    if(callback!=null) callback.onError();
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

    public void submitFeedback(RestaurantObj restaurantObj, CouponObj couponObj, final NetworkCallback callback){
        String url = Router.Restaurants.claimfeedback();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",restaurantObj.id);
            jsonObject.put("questionsAnswered",couponObj.numberOfQuestionsAnswered());
        } catch (JSONException e) {e.printStackTrace();}

        MainApplication.getInstance().addRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Logg.d(TAG, "USER DATA : " + jsonObject.toString());
//                try {
//                    JSONObject result = jsonObject.getJSONObject("data");
//                    JSONArray offersJSON = result.getJSONArray("restaurants");
//                    offers.clear();
//                    offers.addAll(decode(offersJSON));

//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                if(callback!=null) callback.onSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Logg.e(TAG, "ERROR : " + volleyError);
                if(callback!=null) callback.onError();
            }
        });
    }

    public void getMyTransactions(final NetworkCallback2<List<TransactionObj>> callback){
        String url = Router.User.transactions();
        JSONObject jsonObject = new JSONObject();
        MainApplication.getInstance().addRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Logg.d(TAG, "USER DATA : " + jsonObject.toString());
                try {
                    JSONArray transactionsJSON = jsonObject.getJSONArray("data");
                    transactions.clear();
                    transactions.addAll(TransactionObj.decode(transactionsJSON));
                    if(callback!=null) callback.onSuccess(transactions);

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

    public void getUsers(final NetworkCallback2<List<UserObj>> callback){
        String url = Router.Restaurants.clients();
        JSONObject jsonObject = new JSONObject();

        MainApplication.getInstance().addRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Logg.d(TAG, "USER DATA : " + jsonObject.toString());
                try {
                    JSONObject data = jsonObject.getJSONObject("data");
                    JSONArray transactionsJSON = data.getJSONArray("clients");
                    users.clear();
                    users.addAll(UserObj.decode(transactionsJSON));
                    if(callback!=null) callback.onSuccess(users);

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

    public void postBluetoothAvailability(boolean available, String restaurantId, final NetworkCallback2<List<UserObj>> callback){
        Logg.d("BLUETOORHJKLH","postBluetoothAvailability");
        String url = Router.Restaurants.clients();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("clientId","8197711739");
            jsonObject.put("clientName","Maurice");
            jsonObject.put("restaurantId",restaurantId);
            jsonObject.put("isAvailable",available);
        } catch (JSONException e) {e.printStackTrace();}
        MainApplication.getInstance().addRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Logg.d(TAG, "USER DATA : " + jsonObject.toString());
                if(callback!=null) callback.onSuccess(users);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Logg.e(TAG, "ERROR : " + volleyError);
                if(callback!=null) callback.onError();
            }
        });
    }


    public void saveCompleteDataLocally(){
        userMain.saveUserDataLocally();
    }

    public boolean isInProximity(String id){
        return proximityIds.contains(id);
    }

    public void checkIdLeft(){



        Set<String> keys = zoneTimes.keySet();
        Iterator it = zoneTimes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();



            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
}
