package com.mobile.project.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.project.R;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder> {
    public final List<String> displayedGroups;
    private LayoutInflater inflater;
    private final OnItemGroupListener onItemGroupListener;


    public GroupAdapter(List<String> displayedGroups, Context context, OnItemGroupListener onItemGroupListener) {
        this.displayedGroups = displayedGroups;
        inflater = LayoutInflater.from(context);
        this.onItemGroupListener = onItemGroupListener;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.group_cell, parent, false);
        return new GroupViewHolder(view, onItemGroupListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        if (!displayedGroups.isEmpty()) {
            holder.groupName.setText(displayedGroups.get(position));

        }
    }

    @Override
    public int getItemCount() {
        return displayedGroups.size();
    }
    public interface OnItemGroupListener{
        void onItemClick(int position, String groupName);
    }
}
