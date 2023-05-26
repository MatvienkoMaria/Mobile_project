package com.mobile.project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mobile.project.AllGroups;
import com.mobile.project.R;
import com.mobile.project.adapters.GroupAdapter;
import com.mobile.project.pojo.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ControllingActivity extends AppCompatActivity implements GroupAdapter.OnItemGroupListener{
    private RecyclerView groupRecyclerView;
    private AllGroups allGroups =  AllGroups.getInstance();
    private final List<Group> allGroupsList = AllGroups.getInstance().getGroups();
    private final DeleteGroup deleteGroup = new DeleteGroup() {
        @Override
        public void deleteGroup(String deletedGroup) {
            for (int i = 0; i < allGroups.displayedGroupsList.size(); i++){
                if (Objects.equals(allGroups.displayedGroupsList.get(i).name, deletedGroup)){
                    allGroups.displayedGroupsList.remove(allGroups.displayedGroupsList.get(i));
                }
            }
        }
    };

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
        List<String> displayedGroups = new ArrayList<>();
         for (Group group : allGroups.displayedGroupsList){
            displayedGroups.add(group.name);
        }
        GroupAdapter groupAdapter = new GroupAdapter(displayedGroups, this, this, chooseGroup, deleteGroup);
        groupRecyclerView.setAdapter(groupAdapter);
    }
    private ChooseGroup chooseGroup = new ChooseGroup() {
        @Override
        public void chooseGroup(String chosenGroup) {
            for (Group group : allGroups.displayedGroupsList){
                if (group.name.contains(chosenGroup)){
                    allGroups.chosenGroup = group;
                    break;
                }
            }
        }
    };

    public interface ChooseGroup{
        void chooseGroup(String chosenGroup);
    }

    public interface DeleteGroup{
        void deleteGroup(String deletedGroup);
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