package com.mobile.project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.project.AllGroups;
import com.mobile.project.R;
import com.mobile.project.adapters.CalendarAdapter;
import com.mobile.project.adapters.LessonAdapter;
import com.mobile.project.pojo.Group;
import com.mobile.project.pojo.Subject;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private RecyclerView lessonsRecyclerview;
    private LocalDate selectedDate;
    private final ChangeMonthByCell changeMonthByCell = new ChangeMonthByCell() {
        @Override
        public void changeMonthByCell(int prevOrNext, int day_cell) {
            if (prevOrNext == 0){
                selectedDate = selectedDate.minusMonths(1);
                setMonthView(day_cell);
            }
            if (prevOrNext == 1){
                selectedDate = selectedDate.plusMonths(1);
                setMonthView(day_cell);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        AllGroups x = AllGroups.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView(selectedDate.getDayOfMonth());
    }

    private void initWidgets()
    {
        lessonsRecyclerview = findViewById(R.id.lessonsRecyclerView);
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYear);
    }

    private void setMonthView(int day_cell)
    {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this, this, changeMonthByCell, day_cell);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        LocalDate dateLast;
        if(date.getMonth().getValue()!=1) {
            dateLast = LocalDate.of(date.getYear(),
                    date.getMonth().getValue() - 1,
                    date.getDayOfMonth());
        }
        else{
            dateLast = LocalDate.of(date.getYear()-1, 12, 1);
        }

        YearMonth yearMonthLast = YearMonth.from(dateLast);

        int daysInPastMonth = yearMonthLast.lengthOfMonth();
        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue()-1;

        int dayNextMonth = 1;

        for(int i = 1; i <= 42; i++)
        {
            if(i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add(String.valueOf(dayNextMonth));
                dayNextMonth++;
            } else if (i <= dayOfWeek) {
                daysInMonthArray.add(String.valueOf(daysInPastMonth-dayOfWeek+i));
            } else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return  daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction(View view)
    {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView(-1);
    }

    public void nextMonthAction(View view)
    {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView(-1);
    }

    @Override
    public void onItemClick(String dayText)
    {
        //if(!dayText.equals(""))
        //{
        //    String message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate);
        //    Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        //}
        // надо будет переработать для нажатия на день, это чисто проверка отображения предметов
        String dayMonth;
        if (dayText.length() == 1){
            dayText = "0"+dayText;
        }
        if (String.valueOf(selectedDate.getMonthValue()).length() == 1){
            dayMonth = dayText+".0"+String.valueOf(selectedDate.getMonthValue());
        }
        else {
            dayMonth = dayText+"."+String.valueOf(selectedDate.getMonthValue());
        }
        Group chosenGroup = AllGroups.getInstance().chosenGroup;
        List<Subject> subjects = new ArrayList<>();
        for (Subject subject: chosenGroup.subjects){
            if (subject.daysMonth.contains(dayMonth)){
                subjects.add(subject);
            }
        }
        //List<Subject> subjects = new ArrayList<>();
        //subjects.add(new Subject("Какой-то предмет 1","Биба Б.Б.","10:30","11:50","24.05","лк",null));
        //subjects.add(new Subject("Какой-то предмет 2","Бoба Б.Б.","12:40","14:00","24.05","пр",null));
        LessonAdapter lessonAdapter = new LessonAdapter(subjects, this);
        lessonsRecyclerview.setAdapter(lessonAdapter);
    }

    public void toControllingAction(View view) {
        Intent intent = new Intent(this, ControllingActivity.class);
        startActivity(intent);
    }

    public interface ChangeMonthByCell{
        void changeMonthByCell(int prevOrNext, int day_cell);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}