package com.me.keepme.keepme.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by JiginVp on 6/29/2017.
 */

public class Pref {
    private Context context;
    private String PREF_NAME = "LoginPref";
    private String PASS = "Password";
    private SharedPreferences pref;

    public Pref(Context context){
        pref = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        this.context = context;
    }

    public void savePref(String pass){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PASS,pass);
        editor.commit();
        editor.apply();
    }

    public String getPref(){
        return pref.getString(PASS,"");
    }
}
