package com.inguana.movierama.adapters;

import java.util.Set;

public interface HomeActivityFunctionalities {

    Set<String> getFavoriteSet();

    void setFavoriteSet(String favoriteName);

    void removeFavoriteSet(String removeName);
}
