package com.musicplayer.kevin.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kevin on 2016/4/24.
 */
public class PrefUtils {

    private static final String PREF_NAME = "config";

    public static boolean getBoolean(Context context,String key,boolean defaultValue){

        SharedPreferences sp = context.getSharedPreferences(
                PREF_NAME,Context.MODE_PRIVATE);

        return sp.getBoolean(key,defaultValue);
    }

    public static void setBoolean(Context context,String key,boolean defaultValue){
        SharedPreferences sp = context.getSharedPreferences(
                PREF_NAME,Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,defaultValue).commit();
    }


    public static String getString(Context ctx, String key, String defaultValue) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static void setString(Context ctx, String key, String value) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }
}
