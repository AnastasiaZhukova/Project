package com.github.anastasiazhukova.imageloader.load;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.github.anastasiazhukova.imageloader.IImageLoaderListener;
import com.github.anastasiazhukova.imageloader.request.ImageRequest;
import com.github.anastasiazhukova.imageloader.result.ImageResult;
import com.github.anastasiazhukova.imageloader.utils.ImageUtils;
import com.github.anastasiazhukova.imageloader.utils.LogUtils;

class LoadListener implements ILoadListener<ImageRequest, ImageResult> {

    private static final String LOG_TAG = LoadListener.class.getSimpleName();

    private Handler mUiHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onLoadStarted(final ImageRequest pRequest) {

        if (pRequest != null) {

            final IImageLoaderListener listener = pRequest.getListener();
            if (listener != null) {
                listener.onLoadStarted();
            }

            final int placeholderResourceId = pRequest.getPlaceHolder();
            if (placeholderResourceId != 0) {
                mUiHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        final ImageView imageView = pRequest.getTarget().get();
                        if (imageView != null) {
                            ImageUtils.setImageResource(imageView, placeholderResourceId);
                        }
                    }
                });
            }
        }

    }

    @Override
    public void onLoadFinished(@NonNull final ImageResult pResult) {
        final ImageRequest request = pResult.getRequest();
        if (request != null) {

            final IImageLoaderListener listener = request.getListener();
            if (listener != null) {
                listener.onLoadFinished();
            }

            final ImageView imageView = request.getTarget().get();
            if (imageView != null) {
                if (pResult.getError() != null) {
                    ImageUtils.setImageResource(imageView, pResult.getRequest().getErrorHolder());
                    LogUtils.logD(LOG_TAG, "Error image is set to" + imageView);
                } else {
                    imageView.setImageBitmap(pResult.getResult());
                    LogUtils.logD(LOG_TAG, "Bitmap is set" + imageView);
                }
            }
        }
    }
}
