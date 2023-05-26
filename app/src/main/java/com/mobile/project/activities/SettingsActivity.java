package com.mobile.project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mobile.project.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }
    public void backToCalendarFromSettings(View view) {
        onBackPressed();
    }

    public void toAboutApp(View view) {
        Intent intent = new Intent(this, AboutAppActivity.class);
        startActivity(intent);
        finish();
    }
}