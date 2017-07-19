package com.appuptechnologies.instantremedy;

import java.util.HashMap;

/**
 * Created by Dhruv on 14-07-2017.
 */

public class Constants {
    public static HashMap<String, String> adminMap = new HashMap<String, String>();
    static void intialize() {
        adminMap.put("722311791310209","Dhruv");
        adminMap.put("695265450658491","Mohit");
    }
    public Constants(){
        intialize();
    }
}
