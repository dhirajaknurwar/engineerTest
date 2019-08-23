package com.example.postsappsample.networkcalls;

import com.example.postsappsample.model.PostDataModel;

import io.reactivex.Observable;

public abstract class MyApiService {

    private static MyApiService service;

    private static void setServiceType() {
        service = new CloudDataService();
    }

    public static MyApiService getService() {
        if (service == null) {
            setServiceType();
        }

        return service;
    }
    public abstract Observable<PostDataModel> getPostDataApiCall();

}
