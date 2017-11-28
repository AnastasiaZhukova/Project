package com.github.anastasiazhukova.imageloader.request;

import android.support.annotation.NonNull;

import com.github.anastasiazhukova.imageloader.utils.LogUtils;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

final class ImageRequestQueque implements IRequestQueque<ImageRequest> {

    private static final String LOG_TAG = ImageRequestQueque.class.getSimpleName();

    private static ImageRequestQueque mInstance;
    private final BlockingDeque<ImageRequest> mQueque;

    private ImageRequestQueque() {

        LogUtils.logD(LOG_TAG, "Created");

        mQueque = new LinkedBlockingDeque<>();
    }

    static ImageRequestQueque getInstance() {
        if (mInstance == null) {
            synchronized (ImageRequestQueque.class) {
                mInstance = new ImageRequestQueque();
            }
        }

        return mInstance;
    }

    @Override
    public ImageRequest takeFirst() throws InterruptedException {

        LogUtils.logD(LOG_TAG, "Try to take first");

        return mQueque.takeFirst();
    }

    @Override
    public void addFirst(@NonNull final ImageRequest pImageRequest) {

        LogUtils.logD(LOG_TAG, "Added: " + pImageRequest.getUrl());

        mQueque.addFirst(pImageRequest);
    }

}
