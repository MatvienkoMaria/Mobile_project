package com.mobile.project.pojo;

import java.util.List;

public class Subject {
    public String name;
    public String time;
    public String teacher;
    public String timeStart;
    public String timeEnd;
    public String day_of_week;
    public String subgroup_name;
    public String room;
    public List<String> date;
    public String type;
    public String dayMonth;

    public Subject(){};

    public Subject(String name, String teacher, String timeStart, String timeEnd, String dayMonth, String type, String subgroup_name) {
        this.name = name;
        this.teacher = teacher;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.dayMonth = dayMonth;
        this.type = type;
        this.subgroup_name = subgroup_name;
    }
}
