package com.example.postsappsample.base;

public interface CommonNavigator {
    void handleError(Throwable throwable);

    void loadProgressBar(boolean showProgress);
}
