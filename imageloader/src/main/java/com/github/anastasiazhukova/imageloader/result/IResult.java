package com.github.anastasiazhukova.imageloader.result;

public interface IResult<T> {

    T getResult();

    Throwable getError();
}
