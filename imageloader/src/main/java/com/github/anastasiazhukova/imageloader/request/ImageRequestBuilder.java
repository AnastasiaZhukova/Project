package com.github.anastasiazhukova.imageloader.request;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.github.anastasiazhukova.imageloader.IImageLoaderListener;
import com.github.anastasiazhukova.imageloader.utils.LogUtils;

import java.lang.ref.WeakReference;

public final class ImageRequestBuilder {

    private static final String LOG_TAG = ImageRequestBuilder.class.getSimpleName();

    private final IListener mListener;
    String mUrl;
    WeakReference<ImageView> mTarget;
    Integer mPlaceHolderResourceId;
    Integer mErrorResourceId;
    IImageLoaderListener mImageLoaderListener;

    public ImageRequestBuilder(final IListener pListener) {
        mListener = pListener;
    }

    public ImageRequestBuilder load(@NonNull final String pUrl) {
        mUrl = pUrl;
        return this;
    }

    public ImageRequestBuilder into(@NonNull final ImageView pImageView) {
        mTarget = new WeakReference<>(pImageView);
        return this;
    }

    public ImageRequestBuilder placeholder(final int pResourceId) {
        mPlaceHolderResourceId = pResourceId;
        return this;
    }

    public ImageRequestBuilder errorholder(final int pResourceId) {
        mErrorResourceId = pResourceId;
        return this;
    }

    public ImageRequestBuilder listener(final IImageLoaderListener pListener) {
        mImageLoaderListener = pListener;
        return this;
    }

    public void start() {

        LogUtils.logD(LOG_TAG, "Load start called");

        mListener.onRequestReady(this.build());
    }

    private ImageRequest build() {

        LogUtils.logD(LOG_TAG, "Image request built with params:\n" +
                "Target: " + mTarget.get() + "\n" +
                "Url: " + mUrl + "\n" +
                "ErrorHolderId: " + mErrorResourceId + "\n" +
                "PlaceHolderId: " + mPlaceHolderResourceId);

        return new ImageRequest(this);
    }

    public interface IListener {

        void onRequestReady(ImageRequest pImageRequest);
    }

}
