package com.maurice.cryptothon.app.Utils;

import android.net.Uri;

import com.maurice.cryptothon.app.MainApplication;


/**
 * This contains all the routes to our backend server. This is similar to API CONTRACT given to backend team.
 * Any changes with backend API routes will only be reflected by changes in this FIle.
 */
public class Router {

    private static final String DEFAULT_SCHEME = "http";
    private static final String DEFAULT_AUTHORITY = Settings.BASE_AUTHORITY;//"api.maurice.com"
    private static final String API_VERSION = "2.3.2";

    public static String getServerToken() {
        return MainApplication.getInstance().data.userMain.userId;
    };

    private static Uri.Builder getNewDefaultBuilder() {
        return new Uri.Builder().scheme(DEFAULT_SCHEME).encodedAuthority(DEFAULT_AUTHORITY);
    }


    public static class Data{

        public static String transactions(){
            return getNewDefaultBuilder().path("data/transactions")
                    .appendQueryParameter("access_token", getServerToken()).build().toString();
        }

        public static String mycards(){
            return getNewDefaultBuilder().path("data/mycards")
                    .appendQueryParameter("access_token", getServerToken()).build().toString();
        }

        public static String regexs(){
            return getNewDefaultBuilder().path("data/allregexs")
                    .appendQueryParameter("access_token", getServerToken()).build().toString();
        }
    }
    public static class Login{

        public static String main(){
            return getNewDefaultBuilder().path("api").appendPath("user").build().toString();
        }

    }

    public static class Restaurants {

        static Uri.Builder getSubPath(){
            return getNewDefaultBuilder().path("cryptothon/api/restaurants");
        }

        public static String all(){
            return getSubPath().build().toString();
        }
        public static String one(String id){
            return getSubPath().appendPath("id").appendQueryParameter("id",id).build().toString();
        }
        public static String claim(){
            return getSubPath().appendPath("claim").build().toString();
        }
        public static String claimfeedback(){
            return getSubPath().appendPath("claimfeedback").build().toString();
        }
        public static String completed(){
            return getNewDefaultBuilder().path("api/porter-request").build().toString();
        }

        public static String clients(){
            return getSubPath().appendPath("clients").appendQueryParameter("restaurantId","B1234").build().toString();
        }

        public static String claimedClients(){
            return getSubPath().appendPath("claimedClients").appendQueryParameter("restaurantId","B1234").build().toString();
        }

    }

    public static class User{

        static Uri.Builder getSubPath(){
            return getNewDefaultBuilder().path("cryptothon/api/users");
        }
        public static String transactions(){
            return getSubPath().appendPath("getAllTransactions").build().toString();
        }


        public static String allUsers(){
            return getSubPath().appendPath("allUsers").build().toString();
        }
    }
}


