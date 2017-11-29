package com.github.anastasiazhukova.imageloader.load;

import com.github.anastasiazhukova.imageloader.request.ImageRequest;
import com.github.anastasiazhukova.imageloader.result.ImageResult;

public interface ILoadListenerFactory {

    ILoadListener<ImageRequest, ImageResult> createLoadListener();

}
