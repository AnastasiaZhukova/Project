package com.github.anastasiazhukova.imageloader.loadlistener;

import android.support.annotation.NonNull;

import com.github.anastasiazhukova.imageloader.request.IRequest;
import com.github.anastasiazhukova.imageloader.result.IResult;

public interface ILoadListener<X extends IRequest, Y extends IResult> {

    void onLoadStarted(X pRequest);

    void onLoadFinished(@NonNull Y pResult);

}
