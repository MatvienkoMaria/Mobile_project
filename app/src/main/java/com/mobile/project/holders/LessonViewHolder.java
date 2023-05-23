package com.mobile.project.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.project.R;

public class LessonViewHolder extends RecyclerView.ViewHolder{

    public final TextView lessonName;
    public final TextView teacherName;
    public final TextView timeStart;
    public final TextView timeEnd;
    public final TextView typeOfLesson;
    public final View view_cell_lesson_layout;

    public LessonViewHolder(@NonNull View itemView) {
        super(itemView);
        lessonName = itemView.findViewById(R.id.lessonText);
        teacherName = itemView.findViewById(R.id.teacherText);
        timeStart = itemView.findViewById(R.id.timeStart);
        timeEnd = itemView.findViewById(R.id.timeEnd);
        typeOfLesson = itemView.findViewById(R.id.lectionPracticeText);
        view_cell_lesson_layout = itemView.findViewById(R.id.lesson_cell_view);
    }
}
