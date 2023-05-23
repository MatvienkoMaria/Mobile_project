package com.mobile.project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.project.R;
import com.mobile.project.adapters.CalendarAdapter;
import com.mobile.project.adapters.LessonAdapter;

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
        // надо будет переработать для нажатия на день, это чисто проверка отображения предметов
        List<String> lessons = new ArrayList<>();
        lessons.add("Какой-то предмет 1");
        lessons.add("Какой-то предмет 2");
        List<String> teachers = new ArrayList<>();
        teachers.add("Биба Б.Б.");
        teachers.add("Боба Б.Б.");
        List<String> timeStart = new ArrayList<>();
        timeStart.add("10:30");
        timeStart.add("12:40");
        List<String> timeEnd = new ArrayList<>();
        timeEnd.add("11:50");
        timeEnd.add("14:00");
        List<String> typeLesson = new ArrayList<>();
        typeLesson.add("лк");
        typeLesson.add("пр");
        LessonAdapter lessonAdapter = new LessonAdapter(lessons, teachers, timeStart, timeEnd,typeLesson,this);
        lessonsRecyclerview.setAdapter(lessonAdapter);
        // это календарик и месяц, не трогать
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
    public void onItemClick(int position, String dayText)
    {
        if(!dayText.equals(""))
        {
            String message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
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