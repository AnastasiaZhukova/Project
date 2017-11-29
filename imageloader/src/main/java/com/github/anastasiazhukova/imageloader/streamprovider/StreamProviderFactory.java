package com.github.anastasiazhukova.imageloader.streamprovider;

public class StreamProviderFactory implements IStreamProviderFactory {

    @Override
    public IStreamProvider getHttpStreamProvider() {
        return new HttpStreamProvider();
    }
}
