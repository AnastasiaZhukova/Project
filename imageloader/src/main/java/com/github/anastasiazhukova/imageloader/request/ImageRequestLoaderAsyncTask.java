package com.github.anastasiazhukova.imageloader.request;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.github.anastasiazhukova.imageloader.cache.LruCacheManager;
import com.github.anastasiazhukova.imageloader.result.ImageResult;
import com.github.anastasiazhukova.imageloader.streamprovider.HttpStreamProvider;
import com.github.anastasiazhukova.imageloader.streamprovider.IStreamProvider;
import com.github.anastasiazhukova.imageloader.utils.BitmapUtils;
import com.github.anastasiazhukova.imageloader.utils.LogUtils;

import java.io.IOException;
import java.io.InputStream;

class ImageRequestLoaderAsyncTask extends AsyncTask<Void, Void, ImageResult> {

    private final String LOG_TAG = ImageRequestLoaderAsyncTask.class.getSimpleName();
    private IListener mListener;
    private ImageRequest mImageRequest;

    @Override
    protected ImageResult doInBackground(final Void... params) {

        Log.d(LOG_TAG, "doInBackground");

        final ImageResult result;

        try {
            mImageRequest = getRequestFromQueque();
        } catch (final InterruptedException pE) {
            result = new ImageResult();
            result.setError(pE);
            return result;
        }

        if (mListener != null) {
            mListener.onLoadStarted();
        }

        Log.d(LOG_TAG, "URL: " + mImageRequest.getUrl());

        result = new ImageResult(mImageRequest);
        Bitmap bitmap;

        //Try get from cache
        bitmap = getFromCache(mImageRequest.getKey());

        //Try to load from input stream
        if (bitmap == null) {
            final IStreamProvider<String> streamProvider = getStreamProvider();
            final InputStream inputStream;
            try {
                inputStream = streamProvider.get(mImageRequest.getUrl());
                bitmap = getFromInputStream(inputStream);
                if (bitmap != null) {
                    result.setResult(bitmap);
                }
            } catch (final IOException pE) {
                result.setError(pE);
            }
        }

        return result;
    }

    @Override
    protected void onPostExecute(final ImageResult pImageResult) {
        if (mListener != null) {
            mListener.onLoadFinished(pImageResult);
        }
    }

    @NonNull
    private ImageRequest getRequestFromQueque() throws NullPointerException, InterruptedException {

        final ImageRequestQueque instance = ImageRequestQueque.getInstance();

        final ImageRequest imageRequest = instance.takeFirst();
        LogUtils.logD(LOG_TAG, "ImageRequest: " + imageRequest);

        if (imageRequest == null) {
            throw new NullPointerException("Queque is empty");
        }

        return imageRequest;
    }

    private Bitmap getFromCache(final String pKey) {

        return LruCacheManager.getInstance().get(pKey);
    }

    private IStreamProvider<String> getStreamProvider() {
        return new HttpStreamProvider();
    }

    private Bitmap getFromInputStream(final InputStream pInputStream) throws IOException {
        return BitmapUtils.getScaledBitmap(pInputStream, mImageRequest.getWidth(), mImageRequest.getHeight());
    }

    @NonNull
    ImageRequest getCurrentLoadingRequest() {
        return mImageRequest;
    }

    void setListener(final IListener pListener) {
        mListener = pListener;
    }

    interface IListener {

        void onLoadStarted();

        void onLoadFinished(ImageResult pImageResult);
    }

}
