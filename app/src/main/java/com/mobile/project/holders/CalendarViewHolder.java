package com.mobile.project.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.project.R;
import com.mobile.project.adapters.CalendarAdapter;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public final TextView dayOfMonth;
    private final CalendarAdapter.OnItemListener onItemListener;
    public final View view_cell_layout;

    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener) {
        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);
        view_cell_layout = itemView.findViewById(R.id.cell_view);
    }

    @Override
    public void onClick(View view) {
        onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText());
    }
}
