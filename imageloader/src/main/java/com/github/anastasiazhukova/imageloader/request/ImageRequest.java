package com.github.anastasiazhukova.imageloader.request;

import android.widget.ImageView;

import com.github.anastasiazhukova.imageloader.IImageLoaderListener;

import java.lang.ref.WeakReference;

public class ImageRequest implements IRequest {

    private final WeakReference<ImageView> mTarget;
    private int mWidth;
    private int mHeight;

    private final String mUrl;

    private final Integer mErrorHolderResourceId;
    private final Integer mPlaceHolderResourceId;

    private IImageLoaderListener mListener;

    ImageRequest(final ImageRequestBuilder pBuilder) {

        mTarget = pBuilder.mTarget;
        mUrl = pBuilder.mUrl;
        mPlaceHolderResourceId = pBuilder.mPlaceHolderResourceId;
        mErrorHolderResourceId = pBuilder.mErrorResourceId;
        mListener = pBuilder.mImageLoaderListener;

    }

    public void setSize(final int pWidth, final int pHeight) {
        mWidth = pWidth;
        mHeight = pHeight;

    }

    public WeakReference<ImageView> getTarget() {
        return mTarget;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getKey() {
        return getUrl();
    }

    public int getPlaceHolder() {
        return mPlaceHolderResourceId == null ? 0 : mPlaceHolderResourceId;
    }

    public int getErrorHolder() {
        return mErrorHolderResourceId == null ? 0 : mErrorHolderResourceId;
    }

    public IImageLoaderListener getListener() {
        return mListener;
    }

    protected void setListener(final IImageLoaderListener pListener) {
        mListener = pListener;
    }

}
