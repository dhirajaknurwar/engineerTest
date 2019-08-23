package com.example.postsappsample.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.postsappsample.R;
import com.example.postsappsample.interfaces.OnPostClickedInterface;
import com.example.postsappsample.model.PostDataModel;

import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.VIewHolder> {

    private Context context;
    List<PostDataModel.hitsList> mPostLists;
    public OnPostClickedInterface delegate;

    public PostListAdapter(Context context, List<PostDataModel.hitsList> mPostLists) {
        this.context = context;
        this.mPostLists = mPostLists;
    }


    @NonNull
    @Override
    public VIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post_row, parent, false);
        return new VIewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull VIewHolder holder, final int position) {
        final PostDataModel.hitsList list = mPostLists.get(position);
        holder.mTitleTextView.setText(String.valueOf(list.getTitle()));
        holder.mCreatedOnTextView.setText(String.valueOf(list.getCreated_at()));

        holder.selectDeselectPost.setChecked(!list.isDisabled());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delegate.onPostClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPostLists.size();
    }

    public class VIewHolder extends RecyclerView.ViewHolder {

        TextView mTitleTextView;
        TextView mCreatedOnTextView;
        SwitchCompat selectDeselectPost;
        ConstraintLayout constraintLayout;

        public VIewHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTextView = itemView.findViewById(R.id.postTitleTv);
            mCreatedOnTextView = itemView.findViewById(R.id.postCreateOnTV);
            selectDeselectPost = itemView.findViewById(R.id.selectDeselectPost);
            constraintLayout = itemView.findViewById(R.id.itemCL);
        }
    }
}
