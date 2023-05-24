package com.mobile.project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.project.R;
import com.mobile.project.holders.LessonViewHolder;
import com.mobile.project.pojo.Subject;

import java.util.List;
import java.util.Objects;

public class LessonAdapter extends RecyclerView.Adapter<LessonViewHolder> {
    private final List<Subject> subjects;
    private final LayoutInflater inflater;

    public LessonAdapter(List<Subject> subjects, Context context) {
        this.subjects = subjects;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lesson_cell, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        if (!subjects.isEmpty()){
            holder.lessonName.setText(subjects.get(position).name);
            holder.teacherName.setText(subjects.get(position).teacher);
            holder.timeStart.setText(subjects.get(position).timeStart);
            holder.timeEnd.setText(subjects.get(position).timeEnd);
            holder.typeOfLesson.setText(subjects.get(position).typeOfLesson);
            if (Objects.equals(subjects.get(position).typeOfLesson, "лк")){
                holder.view_cell_lesson_layout.findViewById(R.id.lectionPracticeText).setBackgroundResource(R.drawable.lecture_background);
            }
            else {
                holder.view_cell_lesson_layout.findViewById(R.id.lectionPracticeText).setBackgroundResource(R.drawable.practice_background);
            }
        }
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }
    public interface OnItemLessonListener{
        void onItemClick(int position, String lessonName);
    }
}
