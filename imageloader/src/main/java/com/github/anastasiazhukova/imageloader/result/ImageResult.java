package com.github.anastasiazhukova.imageloader.result;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.github.anastasiazhukova.imageloader.request.ImageRequest;
import com.github.anastasiazhukova.imageloader.utils.LogUtils;

public class ImageResult implements IResult<Bitmap> {

    private static final String LOG_TAG = ImageResult.class.getSimpleName();

    private ImageRequest mRequest;
    private Bitmap mBitmap;
    private Throwable mThrowable;

    public ImageResult() {
    }

    public ImageResult(@NonNull final ImageRequest pRequest) {
        mRequest = pRequest;
    }

    public ImageRequest getRequest() {
        return mRequest;
    }

    @Override
    public Bitmap getResult() {
        return mBitmap;
    }

    @Override
    public Throwable getError() {
        return mThrowable;
    }

    public void setResult(final Bitmap pBitmap) {

        LogUtils.logD(LOG_TAG, "Bitmap set");
        mBitmap = pBitmap;
    }

    public void setError(final Exception pThrowable) {
        LogUtils.logE(LOG_TAG, "Exception is set: " + pThrowable.getMessage());
        mThrowable = pThrowable;
    }
}
