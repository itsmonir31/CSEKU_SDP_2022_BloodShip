package com.example.bloodship.others;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

    public static void saveSP(Context cttx, String name, String value){
        SharedPreferences sp = cttx.getSharedPreferences("credentials", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(name, value);
        edit.apply();
    }

    public static String readSP(Context cttx, String name, String defValue){
        SharedPreferences sp = cttx.getSharedPreferences("credentials", Context.MODE_PRIVATE);
        return sp.getString(name, defValue);
    }
}
