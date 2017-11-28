package com.github.anastasiazhukova.imageloader.utils;

public final class CacheUtils {

    private static final String LOG_TAG = CacheUtils.class.getSimpleName();
    private static final int MAX_MEMORY_FOR_IMAGES = 64 * 1024 * 1024;

    public static int getCacheSize() {

        final int cacheSize = Math.min((int) (Runtime.getRuntime().maxMemory() / 4), MAX_MEMORY_FOR_IMAGES);

        LogUtils.logI(LOG_TAG, "Cache size: " + cacheSize);

        return cacheSize;
    }

}
