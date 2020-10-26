package com.game.magictower.util;

import android.util.Log;

public class LogUtil {
    
    private static final boolean DBG = true;

    public static void i(String tag, String msg) {
        if (DBG) Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (DBG) Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (DBG) Log.w(tag, msg);
    }

    public static void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }
}
