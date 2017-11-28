package com.github.anastasiazhukova.imageloader.executor;

import com.github.anastasiazhukova.imageloader.utils.LogUtils;

import java.util.concurrent.Executor;

public class ExecutorFactory implements IExecutorFactory {

    private static final String LOG_TAG = ExecutorFactory.class.getSimpleName();

    @Override
    public Executor createThreadExecutor(final int pNumOfThreads) throws IllegalStateException {

        LogUtils.logD(LOG_TAG, "created thread executor");
        if (pNumOfThreads <= 0) {
            throw new IllegalStateException("Wrong number of threads");
        }

        return new ThreadExecutorService(pNumOfThreads);
    }
}
