package com.mobile.project.pojo;

public class Subject {
    public String name;
    public String teacher;
    public String timeStart;
    public String timeEnd;
    public String dayMonth;
    public String typeOfLesson;
    public String subgroup_name;

    public Subject(String name, String teacher, String timeStart, String timeEnd, String dayMonth, String typeOfLesson, String subgroup_name) {
        this.name = name;
        this.teacher = teacher;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.dayMonth = dayMonth;
        this.typeOfLesson = typeOfLesson;
        this.subgroup_name = subgroup_name;
    }
}
