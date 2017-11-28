package com.github.anastasiazhukova.imageloader.streamprovider;

import com.github.anastasiazhukova.imageloader.utils.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpStreamProvider implements IStreamProvider<String> {

    private static final String LOG_TAG = HttpStreamProvider.class.getSimpleName();

    @Override
    public InputStream get(final String path) throws IOException {
        final URL url = new URL(path);

        LogUtils.logD(LOG_TAG, "Url: " + url);

        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        return connection.getInputStream();
    }

}
