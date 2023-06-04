package com.example.android.asimov2023.retrofit.Model;

public class Announcement {

    private Long id;
    private String title;
    private String description;
    private Integer directorId;

    public Announcement() {
    }

    public Integer getDirectorId() {
        return directorId;
    }

    public Announcement setDirectorId(Integer directorId) {
        this.directorId = directorId;
        return this;
    }


    public Announcement(Long id, String title, String description, Integer directorId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.directorId = directorId;
    }

    public Long getId() {
        return id;
    }

    public Announcement setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Announcement setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Announcement setDescription(String description) {
        this.description = description;
        return this;
    }
}
