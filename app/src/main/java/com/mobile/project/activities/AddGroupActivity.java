package com.mobile.project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobile.project.AllGroups;
import com.mobile.project.R;
import com.mobile.project.adapters.SpinnerAdapter;
import com.mobile.project.pojo.Group;

import java.util.ArrayList;
import java.util.List;

public class AddGroupActivity extends AppCompatActivity {
    private AllGroups allGroups =  AllGroups.getInstance();
    private String chosenItemText;
    private  List<Group> allGroupsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group);

        Spinner spinner = findViewById(R.id.spinner);
        allGroupsList = AllGroups.getInstance().getGroups();
        List<String> namesOfGroups = new ArrayList<>();
        for (Group group : allGroupsList){
            namesOfGroups.add(group.name);
        }
        //Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        SpinnerAdapter adapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, namesOfGroups);
        //Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                chosenItemText = (String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }

    public void backToCalendarFromAddGroup(View view) {
        onBackPressed();
    }
    public void addGroup(View view) {
        String message = "Группа " + chosenItemText + " добавлена.";
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        for (Group group: allGroupsList){
            if (group.name.equals(chosenItemText) && !allGroups.displayedGroupsList.contains(group)){
                allGroups.displayedGroupsList.add(group);
            }
        }
        for (Group group: allGroups.displayedGroupsList){
            if (group.name.equals(chosenItemText)){
                allGroups.chosenGroup = group;
                break;
            }
        }
        finish();
    }
}