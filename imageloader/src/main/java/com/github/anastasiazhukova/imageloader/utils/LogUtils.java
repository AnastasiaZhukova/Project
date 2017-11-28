package com.github.anastasiazhukova.imageloader.utils;

import android.util.Log;

public final class LogUtils {

    public static void logD(final String pTag, final String pMessage) {
        Log.d(pTag, pMessage);
    }

    public static void logE(final String pTag, final String pMessage) {
        Log.e(pTag, pMessage);
    }

    public static void logI(final String pTag, final String pMessage) {
        Log.i(pTag, pMessage);
    }

}
