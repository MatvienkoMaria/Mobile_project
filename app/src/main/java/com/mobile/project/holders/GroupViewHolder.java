package com.mobile.project.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.project.R;
import com.mobile.project.adapters.GroupAdapter;

public class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public final TextView groupName;
    public final GroupAdapter.OnItemGroupListener onItemGroupListener;
    public final View view_cell_group_layout;
    public GroupViewHolder(@NonNull View itemView, GroupAdapter.OnItemGroupListener onItemGroupListener) {
        super(itemView);
        groupName = itemView.findViewById(R.id.cellGroupText);
        this.onItemGroupListener = onItemGroupListener;
        itemView.setOnClickListener(this);
        view_cell_group_layout = itemView.findViewById(R.id.group_cell_view);
    }

    @Override
    public void onClick(View view) {
        onItemGroupListener.onItemClick(getAdapterPosition(), (String) groupName.getText());
    }
}
