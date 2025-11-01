package com.skills.entity;

public class Skill {

    private Long id;
    private String name;
    private int progress;

    public Skill() {
    }

    public Skill(Long id, String name, int progress) {
        this.id = id;
        this.name = name;
        this.progress = progress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
