package com.mobile.project.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.project.R;
import com.mobile.project.adapters.CalendarAdapter;

public class CalendarViewHolder extends RecyclerView.ViewHolder{
    public final TextView dayOfMonth;
    public final View view_cell_layout;

    public CalendarViewHolder(@NonNull View itemView) {
        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        view_cell_layout = itemView.findViewById(R.id.cell_view);
    }
}
