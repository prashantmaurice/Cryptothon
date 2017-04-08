package com.maurice.cryptothon.app.Utils;

/**
 * Created by maurice on 10/12/15.
 */
public interface NetworkCallback2<T> {
    void onSuccess(T t);
    void onError();
}

