package com.example.postsappsample.views;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.postsappsample.R;
import com.example.postsappsample.adapters.PostListAdapter;
import com.example.postsappsample.base.CommonNavigator;
import com.example.postsappsample.interfaces.OnPostClickedInterface;
import com.example.postsappsample.model.PostDataModel;
import com.example.postsappsample.viewmodel.PostsViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CommonNavigator, OnPostClickedInterface {

    private RecyclerView recyclerView;
    private PostsViewModel mPostsViewModel;
    private PostListAdapter postListAdapter;
    private ProgressBar api_call_progress;
    private int api_in_progress = 0;
    private List<PostDataModel.hitsList> hitsLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {

        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.postSwipeRefreshLayout);
        recyclerView = findViewById(R.id.postRecyclerView);
        api_call_progress = findViewById(R.id.api_call_progress);

        mPostsViewModel = ViewModelProviders.of(this).get(PostsViewModel.class);
        mPostsViewModel.setNavigator(this);
        mPostsViewModel.getPostDataApiCall();
        api_in_progress = 1;
        mPostsViewModel.getmPostDataModelMutableLiveData().observe(this, new Observer<PostDataModel>() {
            @Override
            public void onChanged(PostDataModel postDataModel) {
                //TODO - check data and assign to adapter
                if (postDataModel != null && postDataModel.getData() != null) {
                    swipeRefreshLayout.setRefreshing(false);
                    api_in_progress = 0;
                    recyclerView.setVisibility(View.VISIBLE);
                    hitsLists = new ArrayList<>();
                    hitsLists.addAll(postDataModel.getData());
                    setupRecyclerView(hitsLists);
                }

            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (api_in_progress == 0) {
                    recyclerView.setVisibility(View.GONE);
                    mPostsViewModel.clearViewModel();
                    api_in_progress = 1;
                    mPostsViewModel.getPostDataApiCall();
                }
            }
        });
    }

    private void setupRecyclerView(List<PostDataModel.hitsList> data) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        postListAdapter = new PostListAdapter(this, data);
        postListAdapter.delegate = this;
        recyclerView.setAdapter(postListAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPostsViewModel != null) {
            mPostsViewModel.clearViewModel();
        }
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void loadProgressBar(boolean showProgress) {
        if (api_call_progress != null) {
            api_call_progress.setVisibility(showProgress ? View.VISIBLE : View.INVISIBLE);
        }

    }

    @Override
    public void onPostClicked(int position) {

        if (hitsLists.size() > position) {
            hitsLists.get(position).setDisabled((!hitsLists.get(position).isDisabled()));
            postListAdapter.notifyDataSetChanged();
        }
    }
}
