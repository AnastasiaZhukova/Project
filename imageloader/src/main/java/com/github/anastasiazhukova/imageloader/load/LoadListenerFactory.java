package com.github.anastasiazhukova.imageloader.load;

import com.github.anastasiazhukova.imageloader.request.ImageRequest;
import com.github.anastasiazhukova.imageloader.result.ImageResult;

class LoadListenerFactory implements ILoadListenerFactory {

    @Override
    public ILoadListener<ImageRequest, ImageResult> createLoadListener() {
        return new LoadListener();
    }
}
