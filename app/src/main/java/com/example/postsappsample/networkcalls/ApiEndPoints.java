package com.example.postsappsample.networkcalls;


import com.example.postsappsample.BuildConfig;

public class ApiEndPoints {

    static final String GET_POST_URL = BuildConfig.BASE_URL + "search_by_date?tags=story&page=1";

    private ApiEndPoints() {
        // This class is not publicly instantiable
    }

}
