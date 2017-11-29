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

    @Override
    public void load() {
        final CachedLoadAsyncTask cachedLoadAsyncTask = new CachedLoadAsyncTask(mListenerFactory.createLoadListener());
        cachedLoadAsyncTask.executeOnExecutor(mExecutor);
    }

}
