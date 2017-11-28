package com.github.anastasiazhukova.imageloader.loadlistener;

import com.github.anastasiazhukova.imageloader.IImageLoaderListener;
import com.github.anastasiazhukova.imageloader.request.ImageRequest;
import com.github.anastasiazhukova.imageloader.result.ImageResult;

public interface ILoadListenerFactory {

    ILoadListener<ImageRequest, ImageResult> createImageLoadListener();

    ILoadListener<ImageRequest, ImageResult> createImageLoadListener(IImageLoaderListener pListener);

}
