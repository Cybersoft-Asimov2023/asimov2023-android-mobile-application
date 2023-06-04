package com.example.android.asimov2023.retrofit.Model;

public class CourseItem {
    private int id;
    private int courseId;
    private String name, value;
    private Boolean state;

    public CourseItem(int id, int courseId, String name, String value, Boolean state) {
        this.id = id;
        this.courseId = courseId;
        this.name = name;
        this.value = value;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
