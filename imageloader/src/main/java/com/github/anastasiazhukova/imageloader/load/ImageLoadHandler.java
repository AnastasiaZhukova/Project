package com.github.anastasiazhukova.imageloader.load;

import com.github.anastasiazhukova.imageloader.executor.ExecutorFactory;
import com.github.anastasiazhukova.imageloader.executor.IExecutorFactory;

import java.util.concurrent.Executor;

public class ImageLoadHandler implements ILoadHandler {

    private static final int NUM_OF_THREADS = 3;
    private final Executor mExecutor;
    private final ILoadListenerFactory mListenerFactory;

    public ImageLoadHandler() {
        final IExecutorFactory executorServiceFactory = new ExecutorFactory();
        mExecutor = executorServiceFactory.createThreadExecutor(NUM_OF_THREADS);
        mListenerFactory = new LoadListenerFactory();
    }

    private static final String LOG_TAG = ImageLoadHandler.class.getSimpleName();

    @Override
    public void load() {

        final CachedLoadAsyncTask imageRequestLoaderAsyncTask = new CachedLoadAsyncTask(mListenerFactory.createLoadListener());

        imageRequestLoaderAsyncTask.executeOnExecutor(mExecutor);

    }

/*    private void processLoadStarted(final ImageRequest pImageRequest) {

        LogUtils.logD(LOG_TAG + " onLoadStarted", "Called");

        final IImageLoaderListener listener = pImageRequest.getListener();
        if (listener != null) {
            listener.onLoadStarted();
        }

    }

    private void processLoadFinished(@NonNull final ImageResult pImageResult) {
        LogUtils.logD(LOG_TAG + " onLoadFinished", "Called");

        final ImageRequest request = pImageResult.getRequest();
        if (request != null) {
            final IImageLoaderListener listener = request.getListener();
            if (listener != null) {
                listener.onLoadFinished();
            }
        }
    }*/

}
