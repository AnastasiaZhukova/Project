package com.github.anastasiazhukova.imageloader.request;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.github.anastasiazhukova.imageloader.executor.ExecutorFactory;
import com.github.anastasiazhukova.imageloader.executor.IExecutorFactory;
import com.github.anastasiazhukova.imageloader.loadlistener.ILoadListener;
import com.github.anastasiazhukova.imageloader.result.ImageResult;
import com.github.anastasiazhukova.imageloader.utils.ImageUtils;
import com.github.anastasiazhukova.imageloader.utils.LogUtils;

import java.util.concurrent.Executor;

public class ImageRequestHandler implements IRequestHandler<ImageRequest> {

    private static final String LOG_TAG = ImageRequestHandler.class.getSimpleName();

    private static final int NUM_OF_THREADS = 3;
    private final Executor mExecutor;

    public ImageRequestHandler() {
        final IExecutorFactory executorServiceFactory = new ExecutorFactory();
        mExecutor = executorServiceFactory.createThreadExecutor(NUM_OF_THREADS);
    }

    @Override
    public void makeRequest(@NonNull final ImageRequest pRequest, final ILoadListener pListener) {
        if (pListener != null) {
            pRequest.setListener(pListener);
        }
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

        final ImageRequestLoaderAsyncTask imageRequestLoaderAsyncTask = new ImageRequestLoaderAsyncTask();
        imageRequestLoaderAsyncTask.setListener(new ImageRequestLoaderAsyncTask.IListener() {

            @Override
            public void onLoadStarted() {
                final ImageRequest mImageRequest = imageRequestLoaderAsyncTask.getCurrentLoadingRequest();
                processLoadStarted(mImageRequest);
            }

            @Override
            public void onLoadFinished(final ImageResult pImageResult) {
                processLoadFinished(pImageResult);
            }
        });

        imageRequestLoaderAsyncTask.executeOnExecutor(mExecutor);
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

    private void processLoadStarted(final ImageRequest pImageRequest) {

        LogUtils.logD(LOG_TAG + " onLoadStarted", "Called");

        final ILoadListener listener = pImageRequest.getListener();
        if (listener != null) {
            listener.onLoadStarted(pImageRequest);
        }

    }

    private void processLoadFinished(@NonNull final ImageResult pImageResult) {
        LogUtils.logD(LOG_TAG + " onLoadFinished", "Called");

        final ImageRequest request = pImageResult.getRequest();
        if (request != null) {
            final ILoadListener listener = request.getListener();
            if (listener != null) {
                listener.onLoadFinished(pImageResult);
            }
        }
    }

}
