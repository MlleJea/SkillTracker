package com.skills.entity;

public class Skill {

    private long id;
    private String name;
    private int progress;

    public Skill() {
    }

    public Skill(long id, String name, int progress) {
        this.id = id;
        this.name = name;
        this.progress = progress;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        if(progress <0) {
            this.progress = 0;
        } else if (progress > 100) {
            this.progress = 100;
        } else {
            this.progress = progress;
        }
    }
}
