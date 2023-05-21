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

public class AddGroupActivity extends AppCompatActivity {
    private AllGroups allGroups =  AllGroups.getInstance();
    private String chosenItemText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group);

        Spinner spinner = findViewById(R.id.spinner);
        //Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, allGroups.testStringGroups);
        //Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                chosenItemText = (String)parent.getItemAtPosition(position);
                //textSelectedGroup.setText(item);
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
        allGroups.displayedGroupsList.add(chosenItemText);
        allGroups.lastAddedGroup = chosenItemText;
        String message = "Группа " + chosenItemText + " добавлена.";
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        finish();
    }
}