package com.github.anastasiazhukova.imageloader.streamprovider;

import java.io.IOException;
import java.io.InputStream;

public interface IStreamProvider<T> {

    InputStream get(T path) throws IOException;

}
