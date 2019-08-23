package com.example.postsappsample.networkcalls;

import com.example.postsappsample.model.PostDataModel;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class CloudDataService extends MyApiService {

    //Holds service instance
    private MyService service;

    // Constructor
    CloudDataService() {
        service = new RetrofitHelper().getMyService();
    }

    @Override
    public Observable<PostDataModel> getPostDataApiCall() {
        return service.getPostDataApiCall()
                .subscribeOn(Schedulers.io()) // “work” on io thread
                .observeOn(AndroidSchedulers.mainThread()); // “listen” on UIThread;
    }
}
