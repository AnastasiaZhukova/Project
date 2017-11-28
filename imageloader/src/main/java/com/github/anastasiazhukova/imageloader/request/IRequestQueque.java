package com.github.anastasiazhukova.imageloader.request;

interface IRequestQueque<T> {

    T takeFirst() throws InterruptedException;

    void addFirst(final T pRequest);

}
