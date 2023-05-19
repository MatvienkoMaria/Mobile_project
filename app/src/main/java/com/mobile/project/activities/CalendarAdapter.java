package com.mobile.project.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.project.R;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {

    private final ArrayList<String> daysOfMonth;
    private final OnItemListener onItemListener;
    private LayoutInflater inflater;
    private View pos_cell;
    private MainActivity.ChangeMonthByCell changeMonthByCell;
    private int day_cell;

    public CalendarAdapter(ArrayList<String> daysOfMonth, OnItemListener onItemListener, Context context, MainActivity.ChangeMonthByCell changeMonthByCell, int day_cell) {
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
        inflater = LayoutInflater.from(context);
        this.changeMonthByCell = changeMonthByCell;
        this.day_cell = day_cell;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.16666666);
        return new CalendarViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        holder.dayOfMonth.setText(daysOfMonth.get(position));
        if ((holder.getAdapterPosition() + 1) % 7 == 0){
            holder.dayOfMonth.setTextColor(inflater.getContext().getColor(R.color.gray_days));
        }
        if ((holder.getAdapterPosition() < 7) && (Integer.parseInt(holder.dayOfMonth.getText().toString()) > 8)){
            holder.dayOfMonth.setTextColor(inflater.getContext().getColor(R.color.gray_days));
        }
        else if ((holder.getAdapterPosition() > 27) && (Integer.parseInt(holder.dayOfMonth.getText().toString()) <= 14)){
            holder.dayOfMonth.setTextColor(inflater.getContext().getColor(R.color.gray_days));
        }
        else{
            if (daysOfMonth.get(position).equals(String.valueOf(day_cell))){
                holder.view_cell_layout.setBackgroundResource(R.drawable.chosen_cell_ring);
                pos_cell = holder.view_cell_layout;
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos_cell != null) {
                    pos_cell.setBackgroundResource(R.color.null_color);
                }
                holder.view_cell_layout.setBackgroundResource(R.drawable.chosen_cell_ring);
                pos_cell = holder.view_cell_layout;
            }
        });
        if ((holder.getAdapterPosition() < 7) && (Integer.parseInt(holder.dayOfMonth.getText().toString()) > 8)){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeMonthByCell.changeMonthByCell(0, Integer.parseInt(holder.dayOfMonth.getText().toString()));
                }
            });
        }
        if ((holder.getAdapterPosition() > 27) && (Integer.parseInt(holder.dayOfMonth.getText().toString()) <= 14)){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeMonthByCell.changeMonthByCell(1, Integer.parseInt(holder.dayOfMonth.getText().toString()));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    public interface OnItemListener{
        void onItemClick(int position, String dayText);
    }
}
