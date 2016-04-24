package com.musicplayer.kevin.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kevin on 2016/4/24.
 */
public class PrefUtils {

    private static final String FREE_NAME = "config";

    public static boolean getBoolean(Context context,String key,boolean defaultValue){

        SharedPreferences sp = context.getSharedPreferences(
                FREE_NAME,Context.MODE_PRIVATE);

        return sp.getBoolean(key,defaultValue);
    }

    public static void setBoolean(Context context,String key,boolean defaultValue){
        SharedPreferences sp = context.getSharedPreferences(
                FREE_NAME,Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,defaultValue).commit();
    }


}
