package com.github.anastasiazhukova.imageloader.executor;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ThreadExecutorService implements Executor {

    private final ExecutorService mExecutorService;

    ThreadExecutorService(final int pNumOfThreads) {
        mExecutorService = Executors.newFixedThreadPool(pNumOfThreads);
    }

    @Override
    public void execute(@NonNull final Runnable command) {
        mExecutorService.execute(command);
    }
}
