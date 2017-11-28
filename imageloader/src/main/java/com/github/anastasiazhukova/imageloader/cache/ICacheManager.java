package com.github.anastasiazhukova.imageloader.cache;

import android.support.annotation.NonNull;

interface ICacheManager<X, Y> {

    X get(String pKey);

    void put(@NonNull Y pKey, @NonNull X pObject);

}
