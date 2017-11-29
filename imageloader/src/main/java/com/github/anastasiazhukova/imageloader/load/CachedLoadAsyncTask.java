package com.github.anastasiazhukova.imageloader.load;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.github.anastasiazhukova.imageloader.cache.LruCacheManager;
import com.github.anastasiazhukova.imageloader.request.ImageRequest;
import com.github.anastasiazhukova.imageloader.request.ImageRequestQueque;
import com.github.anastasiazhukova.imageloader.result.ImageResult;
import com.github.anastasiazhukova.imageloader.streamprovider.IStreamProvider;
import com.github.anastasiazhukova.imageloader.streamprovider.IStreamProviderFactory;
import com.github.anastasiazhukova.imageloader.streamprovider.StreamProviderFactory;
import com.github.anastasiazhukova.imageloader.utils.BitmapUtils;
import com.github.anastasiazhukova.imageloader.utils.LogUtils;

import java.io.IOException;
import java.io.InputStream;

class CachedLoadAsyncTask extends AsyncTask<Void, Void, ImageResult> {

    private final String LOG_TAG = CachedLoadAsyncTask.class.getSimpleName();
    private final ILoadListener<ImageRequest, ImageResult> mListener;
    private ImageRequest mImageRequest;

    CachedLoadAsyncTask(final ILoadListener<ImageRequest, ImageResult> pLoadListener) {
        mListener = pLoadListener;
    }

    @Override
    protected ImageResult doInBackground(final Void... params) {
        LogUtils.logD(LOG_TAG, "DoInBackground");
        final ImageResult result;

        try {
            mImageRequest = getRequestFromQueque();
        } catch (final InterruptedException pE) {
            result = new ImageResult();
            result.setError(pE);
            return result;
        }

        if (mListener != null) {
            mListener.onLoadStarted(mImageRequest);
        }

        Log.d(LOG_TAG, "URL: " + mImageRequest.getUrl());

        result = new ImageResult(mImageRequest);
        Bitmap bitmap;

        //Try get from cache
        LogUtils.logI(LOG_TAG, "Try get from cache");
        bitmap = getFromCache(mImageRequest.getKey());
        if (bitmap != null) {
            result.setResult(bitmap);
            return result;
        }

        //Try to load from input stream
        LogUtils.logI(LOG_TAG, "Try get from input stream");
        final IStreamProvider streamProvider = getStreamProvider();
        final InputStream inputStream;
        try {
            inputStream = streamProvider.get(mImageRequest.getUrl());
            bitmap = getFromInputStream(inputStream);
            if (bitmap != null) {
                putInCache(mImageRequest.getKey(), bitmap);
                result.setResult(bitmap);
            }
        } catch (final IOException pE) {
            result.setError(pE);
        }

        return result;
    }

    @Override
    protected void onPostExecute(final ImageResult pImageResult) {
        LogUtils.logD(LOG_TAG, "OnPostExecute");
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

    private void putInCache(final String pKey, final Bitmap pBitmap) {
        LruCacheManager.getInstance().put(pKey, pBitmap);
    }

    private IStreamProvider getStreamProvider() {
        final IStreamProviderFactory streamProviderFactory = new StreamProviderFactory();
        return streamProviderFactory.getHttpStreamProvider();
    }

    private Bitmap getFromInputStream(final InputStream pInputStream) throws IOException {
        return BitmapUtils.getScaledBitmap(pInputStream, mImageRequest.getWidth(), mImageRequest.getHeight());
    }

}
