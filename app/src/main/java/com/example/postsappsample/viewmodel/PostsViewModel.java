package com.example.postsappsample.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.postsappsample.base.BaseViewModel;
import com.example.postsappsample.base.CommonNavigator;
import com.example.postsappsample.model.PostDataModel;
import com.example.postsappsample.networkcalls.MyApiService;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class PostsViewModel extends BaseViewModel<CommonNavigator> {
    private Disposable mDisposable = null;

    private MutableLiveData<PostDataModel> mPostDataModelMutableLiveData;

    public PostsViewModel() {
        mPostDataModelMutableLiveData = new MutableLiveData<>();
        loadData();
    }

    public void loadData() {
        mPostDataModelMutableLiveData.setValue(new PostDataModel());

    }

    public void clearViewModel() {
        mPostDataModelMutableLiveData.setValue(null);
    }

    public MutableLiveData<PostDataModel> getmPostDataModelMutableLiveData() {
        return mPostDataModelMutableLiveData;
    }

    public void getPostDataApiCall() {

        getNavigator().loadProgressBar(true);
        mDisposable = MyApiService.getService().getPostDataApiCall()
                .subscribe(new Consumer<PostDataModel>() {
                    @Override
                    public void accept(PostDataModel postDataModel) throws Exception {
                        getNavigator().loadProgressBar(false);
                        if (postDataModel != null && postDataModel.getData() != null) {
                            mDisposable.dispose();
                            mPostDataModelMutableLiveData.setValue(postDataModel);

                        } else {
                            getNavigator().loadProgressBar(false);
                            mDisposable.dispose();
                        }
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        getNavigator().loadProgressBar(false);
                        getNavigator().handleError(throwable);
                    }
                });

    }
}

