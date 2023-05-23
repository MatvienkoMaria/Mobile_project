package com.mobile.project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.project.R;
import com.mobile.project.holders.LessonViewHolder;

import java.util.List;
import java.util.Objects;

public class LessonAdapter extends RecyclerView.Adapter<LessonViewHolder> {
    private final List<String> lessons;
    private final List<String> teachers;
    private final List<String> timeStart;
    private final List<String> timeEnd;
    private final List<String> typeLesson;
    private final LayoutInflater inflater;

    public LessonAdapter(List<String> lessons, List<String> teachers, List<String> timeStart, List<String> timeEnd, List<String> typeLesson, Context context) {
        this.lessons = lessons;
        this.teachers = teachers;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.typeLesson = typeLesson;
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
        if (!lessons.isEmpty()){
            holder.lessonName.setText(lessons.get(position));
            holder.teacherName.setText(teachers.get(position));
            holder.timeStart.setText(timeStart.get(position));
            holder.timeEnd.setText(timeEnd.get(position));
            holder.typeOfLesson.setText(typeLesson.get(position));
            if (Objects.equals(typeLesson.get(position), "лк")){
                holder.view_cell_lesson_layout.findViewById(R.id.lectionPracticeText).setBackgroundResource(R.drawable.lecture_background);
            }
            else {
                holder.view_cell_lesson_layout.findViewById(R.id.lectionPracticeText).setBackgroundResource(R.drawable.practice_background);
            }
        }
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }
    public interface OnItemLessonListener{
        void onItemClick(int position, String lessonName);
    }
}
