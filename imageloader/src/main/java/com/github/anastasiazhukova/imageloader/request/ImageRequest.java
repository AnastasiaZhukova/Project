package com.github.anastasiazhukova.imageloader.request;

import android.widget.ImageView;

import com.github.anastasiazhukova.imageloader.loadlistener.ILoadListener;

import java.lang.ref.WeakReference;

public class ImageRequest implements IRequest {

    private final WeakReference<ImageView> mTarget;
    private int mWidth;
    private int mHeight;

    private final String mUrl;

    private final Integer mErrorHolderResourceId;
    private final Integer mPlaceHolderResourceId;

    private ILoadListener mListener;

    ImageRequest(final ImageRequestBuilder pBuilder) {

        mTarget = pBuilder.mTarget;
        mUrl = pBuilder.mUrl;
        mPlaceHolderResourceId = pBuilder.mPlaceHolderResourceId;
        mErrorHolderResourceId = pBuilder.mErrorResourceId;

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

    public void setSize(final int pWidth, final int pHeight) {
        mWidth = pWidth;
        mHeight = pHeight;

    }

    String getUrl() {
        return mUrl;
    }

    String getKey() {
        return getUrl();
    }

    public int getPlaceHolder() {
        return mPlaceHolderResourceId == null ? 0 : mPlaceHolderResourceId;
    }

    public int getErrorHolder() {
        return mErrorHolderResourceId == null ? 0 : mErrorHolderResourceId;
    }

    ILoadListener getListener() {
        return mListener;
    }

    void setListener(final ILoadListener pListener) {
        mListener = pListener;
    }

}
