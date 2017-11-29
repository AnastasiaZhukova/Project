package com.github.anastasiazhukova.imageloader;

import android.support.annotation.NonNull;

import com.github.anastasiazhukova.imageloader.request.IRequestHandler;
import com.github.anastasiazhukova.imageloader.request.ImageRequest;
import com.github.anastasiazhukova.imageloader.request.ImageRequestBuilder;
import com.github.anastasiazhukova.imageloader.request.ImageRequestHandler;
import com.github.anastasiazhukova.imageloader.utils.LogUtils;

public final class ImageLoader implements IImageLoader {

    private static final String LOG_TAG = ImageLoader.class.getSimpleName();
    private static volatile ImageLoader mInstance;
    private final IRequestHandler<ImageRequest> mRequestHandler;

    private ImageLoader() {

        LogUtils.logI(LOG_TAG, "Created");
        mRequestHandler = new ImageRequestHandler();

    }

    public static ImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                mInstance = new ImageLoader();
            }
        }
        return mInstance;
    }

    @Override
    public ImageRequestBuilder load(@NonNull final String pUrl) {

        final ImageRequestBuilder.IListener listener = new ImageRequestBuilder.IListener() {

            @Override
            public void onRequestReady(final ImageRequest pImageRequest) {
                handleRequestReadyEvent(pImageRequest);
            }
        };

        final ImageRequestBuilder imageRequestBuilder = new ImageRequestBuilder(listener);
        return imageRequestBuilder.load(pUrl);
    }

    private void handleRequestReadyEvent(final ImageRequest pImageRequest) {
        mRequestHandler.makeRequest(pImageRequest);
    }
}







