package com.maurice.cryptothon.app.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JobObj implements Comparable<JobObj> {

    public String jobId = "";
    public Long startTime = 1457286107L,endTime = 1457386107L;
    public double lat,longg;
    public String address;
    public int cost = 1000;

    public JobObj() {}

    //SERVER ENCODERS
    public static JobObj decode(JSONObject obj){
        JobObj re = new JobObj();
        try {
            re.jobId = (obj.has("jobId")) ? obj.getString("jobId") : null;
            re.startTime = (obj.has("startTime")) ? obj.getLong("startTime") : 0;
            re.endTime = (obj.has("endTime")) ? obj.getLong("endTime") : 0;
            re.lat = (obj.has("lat")) ? obj.getLong("lat") : 0;
            re.longg = (obj.has("longg")) ? obj.getLong("longg") : 0;
            re.address = (obj.has("address")) ? obj.getString("address") : "No address";
            re.cost = (obj.has("cost")) ? obj.getInt("cost") : 0;
        } catch (JSONException e) {e.printStackTrace();}
        return re;
    }

    public static ArrayList<JobObj> decode(JSONArray obj){

        ArrayList<JobObj> list = new ArrayList<>();
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
            jsonObject.put( "jobId", jobId );
            jsonObject.put( "startTime", startTime );
            jsonObject.put( "endTime", endTime );
            jsonObject.put( "cost", cost );

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public int compareTo(JobObj another) {
        return 0;//TODO : implement this
    }

}
