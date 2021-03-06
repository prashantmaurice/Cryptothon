package com.maurice.cryptothon.app.Models;

import android.content.Context;

import com.maurice.cryptothon.app.storage.SharedPrefs;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This contains all the User data excluding kids,
 */
public class UserMain {
    private Context mContext;
    private static UserMain instance;
    private SharedPrefs sPrefs;
    public String name;
    public String userId;
    public String xmppPass;
    public String email;
    public String imageUrl;
    public String gcmId;
    public String facebookId;
    public String phone;
    public String address;
    public String coverPic;
    public Boolean isDoctor = false;
    public String userType; //can takes values "parent","doctor","none"
    public Boolean isMale = false;
    public String token;
    public String authProvider;


    private UserMain(Context context) {
        mContext = context;
        pullUserDataFromLocal();
    }
    public static UserMain getInstance(Context context) {
        if(instance == null) {
            instance = new UserMain(context);
        }
        return instance;
    }


    //LOCAL STORAGE ENCODERS
    public void pullUserDataFromLocal() {
        sPrefs = SharedPrefs.getInstance(mContext);
        try {
            name = (sPrefs.userData.has("name"))?sPrefs.userData.getString("name"):"";
            phone = (sPrefs.userData.has("phone"))?sPrefs.userData.getString("phone"):"";
            userId = (sPrefs.userData.has("userId"))?sPrefs.userData.getString("userId"):"";
        } catch (JSONException e) {e.printStackTrace();}
    }
    public void saveUserDataLocally() {
        try {
            sPrefs.userData.put("userId", userId);
            sPrefs.userData.put("phone", phone);
            sPrefs.userData.put("name", name);
        } catch (JSONException e) {e.printStackTrace();}
        sPrefs.saveUserData();
    }


    //SERVER ENCODERS
    public void decodeFromServer(JSONObject obj2){
        try {
            if(obj2.has("user")){
                JSONObject obj = obj2.getJSONObject("user");
                name = (obj.has("username"))?obj.getString("username"):"";
                userId = (obj.has("userId"))?obj.getString("userId"):"";
                phone = (obj.has("mobileNumber"))?obj.getString("mobileNumber"):"";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean loggedIn() {
        if(userId!=null&&!userId.isEmpty()) return true;
        return false;
    }
}
