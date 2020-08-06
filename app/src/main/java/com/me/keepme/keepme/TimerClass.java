package com.me.keepme.keepme;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by JiginVp on 8/3/2017.
 */

public class TimerClass {

    private static TimerClass t ;
    private TimerClass(){

    }
    public static TimerClass getObj(){
        if(t == null)
            return t = new TimerClass();

        return t;
    }

    private Timer mActivityTransitionTimer;
    private TimerTask mActivityTransitionTimerTask;
    public static boolean wasInBackground;
    private final long MAX_ACTIVITY_TRANSITION_TIME_MS = 2000;


    public void startActivityTransitionTimer() {
        this.mActivityTransitionTimer = new Timer();
        this.mActivityTransitionTimerTask = new TimerTask() {
            public void run() {
                wasInBackground = true;
            }
        };

        this.mActivityTransitionTimer.schedule(mActivityTransitionTimerTask,
                MAX_ACTIVITY_TRANSITION_TIME_MS);
    }

    public void stopActivityTransitionTimer() {
        if (this.mActivityTransitionTimerTask != null) {
            this.mActivityTransitionTimerTask.cancel();
        }

        if (this.mActivityTransitionTimer != null) {
            this.mActivityTransitionTimer.cancel();
        }

        this.wasInBackground = false;
    }

}
