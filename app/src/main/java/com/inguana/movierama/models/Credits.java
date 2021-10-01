package com.inguana.movierama.models;

import java.util.List;

public class Credits {

    private List<Cast> cast;
    private List<Crew> crew;

    public Credits() {
    }

    public Credits(List<Cast> cast, List<Crew> crew) {
        this.cast = cast;
        this.crew = crew;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    @Override
    public String toString() {
        return "Credits{" +
                "cast=" + cast +
                ", crew=" + crew +
                '}';
    }
}
