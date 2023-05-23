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
    private View pos_cell;
    private ControllingActivity.ChooseGroup chooseGroup;

    public GroupAdapter(List<String> displayedGroups, Context context, OnItemGroupListener onItemGroupListener, ControllingActivity.ChooseGroup chooseGroup) {
        this.displayedGroups = displayedGroups;
        inflater = LayoutInflater.from(context);
        this.onItemGroupListener = onItemGroupListener;
        this.chooseGroup = chooseGroup;
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
            visibilityOfCheckChosenGroup(holder);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visibilityOfCheckChosenGroup(holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return displayedGroups.size();
    }
    public interface OnItemGroupListener{
        void onItemClick(int position, String groupName);
    }
    private void visibilityOfCheckChosenGroup(@NonNull GroupViewHolder holder){
        if (pos_cell != null) {
            pos_cell.findViewById(R.id.checkGroup).setVisibility(View.VISIBLE);
        }
        holder.view_cell_group_layout.findViewById(R.id.checkGroup).setVisibility(View.INVISIBLE);
        pos_cell = holder.view_cell_group_layout.findViewById(R.id.checkGroup);
        String chosen = holder.groupName.getText().toString();
        chooseGroup.chooseGroup(chosen);
    }
}
