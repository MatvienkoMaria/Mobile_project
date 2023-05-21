package com.mobile.project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mobile.project.AllGroups;
import com.mobile.project.R;

import java.util.ArrayList;
import java.util.List;

public class ControllingActivity extends AppCompatActivity implements GroupAdapter.OnItemGroupListener{
    private RecyclerView groupRecyclerView;
    private AllGroups allGroups =  AllGroups.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.controlling);
        initWidgets();
        setGroupView();
    }
    private void initWidgets()
    {
        groupRecyclerView = findViewById(R.id.groupRecyclerView);
    }
    private void setGroupView(){
        List<String> displayedGroups = new ArrayList<>(allGroups.displayedGroupsList);
        String lastAddedGroup = allGroups.lastAddedGroup;
        GroupAdapter groupAdapter = new GroupAdapter(displayedGroups, this, this, lastAddedGroup);
        groupRecyclerView.setAdapter(groupAdapter);
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

    @Override
    public void onItemClick(int position, String groupName) {
        if(!groupName.equals(""))
        {
            String message = "Selected Group " + groupName + ".";
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }
}