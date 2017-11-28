package com.github.anastasiazhukova.imageloader.loadlistener;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.github.anastasiazhukova.imageloader.IImageLoaderListener;
import com.github.anastasiazhukova.imageloader.request.ImageRequest;
import com.github.anastasiazhukova.imageloader.result.ImageResult;
import com.github.anastasiazhukova.imageloader.utils.ImageUtils;
import com.github.anastasiazhukova.imageloader.utils.LogUtils;

public class LoadListenerFactory implements ILoadListenerFactory {

    private static final String LOG_TAG = LoadListenerFactory.class.getSimpleName();

    @Override
    public ILoadListener<ImageRequest, ImageResult> createImageLoadListener() {
        return createImageLoadListener(null);
    }

    @Override
    public ILoadListener<ImageRequest, ImageResult> createImageLoadListener(final IImageLoaderListener pListener) {

        final ILoadListener<ImageRequest, ImageResult> mListener = new ILoadListener<ImageRequest, ImageResult>() {

            @Override
            public void onLoadStarted(final ImageRequest pRequest) {

                if (pRequest != null) {
                    final int placeholderResourceId = pRequest.getPlaceHolder();
                    if (placeholderResourceId != 0) {
                        final ImageView imageView = pRequest.getTarget().get();
                        if (imageView != null) {
                            ImageUtils.setImageResource(imageView, placeholderResourceId);
                        }

                    }

                    if (pListener != null) {
                        pListener.onLoadStarted();
                    }
                }
            }

            @Override
            public void onLoadFinished(@NonNull final ImageResult pResult) {
                final ImageRequest request = pResult.getRequest();
                if (request != null)

                {
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

                if (pListener != null) {
                    pListener.onLoadFinished();
                }

            }
        };

        return mListener;
    }

}
