package com.github.anastasiazhukova.imageloader.request;

public interface IRequestHandler<T extends IRequest> {

    void makeRequest(T pRequest);
}
