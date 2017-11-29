package com.github.anastasiazhukova.imageloader;

import com.github.anastasiazhukova.imageloader.request.ImageRequestBuilder;

interface IImageLoader {

    ImageRequestBuilder load(String pUrl);
}
