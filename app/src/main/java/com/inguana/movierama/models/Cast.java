package com.inguana.movierama.models;

public class Cast {

    private String name;

    public Cast() {
    }

    public Cast(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Cast{" +
                "name='" + name + '\'' +
                '}';
    }
}
