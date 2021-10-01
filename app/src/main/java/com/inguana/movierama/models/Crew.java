package com.inguana.movierama.models;

public class Crew {

    private String job;
    private String name;

    public Crew() {
    }

    public Crew(String job, String name) {
        this.job = job;
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Crew{" +
                "job='" + job + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
