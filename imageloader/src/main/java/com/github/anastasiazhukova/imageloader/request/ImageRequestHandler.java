package com.github.anastasiazhukova.imageloader.request;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.github.anastasiazhukova.imageloader.load.ImageLoadHandler;
import com.github.anastasiazhukova.imageloader.utils.ImageUtils;
import com.github.anastasiazhukova.imageloader.utils.LogUtils;

public class ImageRequestHandler implements IRequestHandler<ImageRequest> {

    private static final String LOG_TAG = ImageRequestHandler.class.getSimpleName();

    private final ImageLoadHandler mLoadHandler;

    public ImageRequestHandler() {
        mLoadHandler = new ImageLoadHandler();
    }

    @Override
    public void makeRequest(final ImageRequest pRequest) {
        enqueue(pRequest);
    }

    private void enqueue(@NonNull final ImageRequest pImageRequest) {

        final ImageView imageView = pImageRequest.getTarget().get();

        if (imageView == null) {
            return; //if reference points to noting(i.e. it no longer exists and not going to be drawn
        }

        if (ImageUtils.imageHasSize(pImageRequest)) {
            LogUtils.logI(LOG_TAG, "Dispatch Loading");
            imageView.setTag(pImageRequest.getKey());
            ImageRequestQueque.getInstance().addFirst(pImageRequest);
            dispatchLoading();
        } else {
            LogUtils.logI(LOG_TAG, "Defer Image Request");
            deferImageRequest(pImageRequest);
        }

    }

    @SuppressLint("StaticFieldLeak")
    private void dispatchLoading() {
        mLoadHandler.load();
    }

    private void deferImageRequest(final ImageRequest pImageRequest) {

        final ImageView imageView = pImageRequest.getTarget().get();

        if (imageView != null) {
            imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                @Override
                public boolean onPreDraw() {

                    final ImageView view = pImageRequest.getTarget().get();

                    if (view == null) {
                        return true;
                    }

                    view.getViewTreeObserver().removeOnPreDrawListener(this);

                    if (view.getWidth() > 0 && imageView.getHeight() > 0) {
                        pImageRequest.setSize(view.getWidth(), view.getHeight());
                        enqueue(pImageRequest);
                    }
                    return true;
                }
            });
        }

    }

}
