package com.mobile.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    private ChangeMonthByCell changeMonthByCell = new ChangeMonthByCell() {
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
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYear);
    }

    private void setMonthView(int day_cell)
    {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this, this, changeMonthByCell, day_cell);
        //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        //calendarRecyclerView.setLayoutManager(layoutManager);
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

    public interface ChangeMonthByCell{
        void changeMonthByCell(int prevOrNext, int day_cell);
    }

    //@Override
    //public void onBackPressed() {
    //    super.onBackPressed();
    //}
}