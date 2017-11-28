package com.github.anastasiazhukova.imageloader.executor;

import java.util.concurrent.Executor;

public interface IExecutorFactory {

    Executor createThreadExecutor(int pNumOfThreads);

}
