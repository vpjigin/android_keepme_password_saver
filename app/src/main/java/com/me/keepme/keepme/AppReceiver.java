package com.me.keepme.keepme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by JiginVp on 8/3/2017.
 */

public class AppReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent in= new Intent(context,AppService.class);
        context.startService(in);
    }
}
