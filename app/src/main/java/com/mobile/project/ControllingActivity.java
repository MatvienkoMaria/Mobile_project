package com.mobile.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ControllingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.controlling);
    }

    public void toSettingsAction(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        finish();
    }

    public void toAddGroupAction(View view) {
        Intent intent = new Intent(this, AddGroupActivity.class);
        startActivity(intent);
        finish();
    }

    public void backToCalendarFromControlling(View view) {
        onBackPressed();
    }
}