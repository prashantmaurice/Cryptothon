package com.maurice.cryptothon.app.Utils;


import com.maurice.cryptothon.app.MainApplication;
import com.maurice.cryptothon.app.R;

/**
 * This is the master settings controller.
 * Make sure you have defined local_ip in strings_local
 */
public class Settings {
    enum RunMode{ PROD, BETAPROD,LOCAL }

    /**
     * This is the master settings controller,
     * set it the way you want and make sure you don't push this file with RunMode as RunMode.LOCAL
     */
    private final static RunMode runMode = RunMode.LOCAL;//this thing will override all the below settings //set this thing true for production

    //don't touch below things unless you know what you are doing
    private final static String productionIp = "test.pm.in";
    private final static String betaProductionIp = "test.om.in";
    private final static String localIp = MainApplication.getInstance().getString(R.string.local_ip);
    public final static String BASE_AUTHORITY = (runMode== RunMode.PROD)?productionIp: ((runMode== RunMode.BETAPROD)?betaProductionIp: localIp);

    public final static boolean isDebugMode = !(runMode== RunMode.PROD);
    public final static boolean showDebugToasts = !(runMode== RunMode.PROD);

    enum UserMode { SELLER, BUYER }
    static public boolean isUserSeller(){
        return userMode == UserMode.SELLER;
    }


    private final static UserMode userMode = UserMode.BUYER;
//    private final static UserMode userMode = UserMode.SELLER;
}

