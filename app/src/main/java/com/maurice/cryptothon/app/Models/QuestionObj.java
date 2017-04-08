package com.maurice.cryptothon.app.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.attr.name;

public class QuestionObj implements Comparable<QuestionObj> {
    String TAG = "QUESTION.MODEL";
    public String question = "";

    public int rating = 0;

    public QuestionObj() {}

    //SERVER ENCODERS
    public static QuestionObj decode(JSONObject obj){
        QuestionObj re = new QuestionObj();
        try {
            re.question = (obj.has("question")) ? obj.getString("question") : null;
        } catch (JSONException e) {e.printStackTrace();}
        return re;
    }

    public static ArrayList<QuestionObj> decode(JSONArray obj){

        ArrayList<QuestionObj> list = new ArrayList<>();
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
    public int compareTo(QuestionObj another) {
        return 0;//TODO : implement this
    }

}
