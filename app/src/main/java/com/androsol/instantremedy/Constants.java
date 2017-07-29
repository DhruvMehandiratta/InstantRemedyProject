package com.androsol.instantremedy;

import java.util.HashMap;

/**
 * Created by Dhruv on 14-07-2017.
 */

public class Constants {

    public static String[] adminArray = {"186dkm@gmail.com", "androsoltech@gmail.com", "dhruvmehandirattahere@gmail.com"
    ,"mahajan.akhil@tcs.com", "nishant11.s@tcs.com", "aishwarya.gambhir13@gmail.com", "nagpalmohit3@gmail.com",
            "anj.1903.jain@gmail.com"};
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_PHOTO = "userPhoto";
    public static final String USER_NAME = "userName";



    public static HashMap<String, String> adminMap = new HashMap<String, String>();
    static void intialize() {
        adminMap.put("722311791310209","Dhruv");
    }
    public Constants(){
        intialize();
    }
}
