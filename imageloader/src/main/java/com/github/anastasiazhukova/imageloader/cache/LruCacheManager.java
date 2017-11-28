package com.github.anastasiazhukova.imageloader.cache;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

import com.github.anastasiazhukova.imageloader.utils.CacheUtils;
import com.github.anastasiazhukova.imageloader.utils.LogUtils;

public final class LruCacheManager implements ICacheManager<Bitmap, String> {

    private static final String LOG_TAG = LruCacheManager.class.getSimpleName();

    private static LruCacheManager mCacheManager;
    private final LruCache<String, Bitmap> mCache;
    private final Object lock = new Object();

    private LruCacheManager() {

        mCache = new LruCache<String, Bitmap>(CacheUtils.getCacheSize()) {

            @Override
            protected int sizeOf(final String key, final Bitmap value) {
                return key.length() + value.getByteCount();
            }
        };

        LogUtils.logI(LOG_TAG, "Created");
    }

    public static LruCacheManager getInstance() {
        if (mCacheManager == null) {
            synchronized (LruCacheManager.class) {
                mCacheManager = new LruCacheManager();
            }
        }
        return mCacheManager;
    }

    @Override
    public Bitmap get(final String pKey) {
        synchronized (lock) {
            return mCache.get(pKey);
        }
    }

    @Override
    public void put(@NonNull final String pKey, @NonNull final Bitmap pBitmap) {
        synchronized (lock) {
            mCache.put(pKey, pBitmap);
            LogUtils.logD(LOG_TAG, "Put bitmap by: " + pKey + " key");
        }

    }
}
