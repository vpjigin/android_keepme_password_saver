package com.me.keepme.keepme.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.me.keepme.keepme.R;

/**
 * Created by JiginVp on 6/29/2017.
 */

public class Utils {
    //public static int []icons = {R.drawable.group,R.drawable.other,R.drawable.group,R.drawable.other,R.drawable.group,R.drawable.other};

    public static int flag = 0;
    public static void flagPlus(){
        flag++;
    }

    public static int[] getImageResourse(){
        int[] imgRes = {R.drawable.c,R.drawable.fb,R.drawable.imp,R.drawable.ink,R.drawable.insta,R.drawable.mail,R.drawable.other,
                R.drawable.social,R.drawable.tum,R.drawable.twittr,R.drawable.web};
        return imgRes;
    }
    public static int getImageResourse(int id){
        int[] imgRes = {R.drawable.c,R.drawable.fb,R.drawable.imp,R.drawable.ink,R.drawable.insta,R.drawable.mail,R.drawable.other,
                R.drawable.social,R.drawable.tum,R.drawable.twittr,R.drawable.web};
        return imgRes[id];
    }

    public static boolean isNetworkAvailable(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) return false;
        NetworkInfo.State network = info.getState();
        return (network == NetworkInfo.State.CONNECTED || network == NetworkInfo.State.CONNECTING);
    }
}
