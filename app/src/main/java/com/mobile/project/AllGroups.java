package com.mobile.project;

import com.mobile.project.pojo.Group;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class AllGroups {
    private static AllGroups instance = new AllGroups();
    public List<String> allGroupsList = new ArrayList<>();
    public LinkedHashSet<String> displayedGroupsList = new LinkedHashSet<>();
    public String chosenGroup;

    public AllGroups() {
        allGroupsList.add("РЛ-221");
        allGroupsList.add("РЛ-222");
    }
    public static AllGroups getInstance() {
        return instance;
    }
}
