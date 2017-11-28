package com.github.anastasiazhukova.imageloader.request;

import com.github.anastasiazhukova.imageloader.loadlistener.ILoadListener;

public interface IRequestHandler<T extends IRequest> {

    void makeRequest(T pRequest, ILoadListener pListener);

}
