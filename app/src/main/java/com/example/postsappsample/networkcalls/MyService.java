package com.example.postsappsample.networkcalls;

import com.example.postsappsample.model.PostDataModel;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MyService {

    @GET(ApiEndPoints.GET_POST_URL)
    Observable<PostDataModel> getPostDataApiCall();

}
