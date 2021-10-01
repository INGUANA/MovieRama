package com.inguana.movierama;

import static com.inguana.movierama.utils.Constants.PREF_FAVORITE;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.inguana.movierama.adapters.HomeActivityFunctionalities;
import com.inguana.movierama.popularMovies.MoviesViewModel;
import com.inguana.movierama.utils.Utils;

import java.util.Set;

public class HomeScreenActivity extends BaseActivity implements HomeActivityFunctionalities {

    private NavController navController;

    private static final String TAG = "HomeScreenActivity";
    private MoviesViewModel moviesViewModel;
    private Set<String> favoriteSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_activity);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();
        favoriteSet = Utils.getPreference(this, PREF_FAVORITE);

        moviesViewModel = new ViewModelProvider(this).get(MoviesViewModel.class);
    }

    @Override
    public Set<String> getFavoriteSet() {
        return favoriteSet;
    }

    @Override
    public void setFavoriteSet(String favoriteName) {
        favoriteSet.add(favoriteName);
        Utils.setPreference(this, PREF_FAVORITE, favoriteSet);
    }

    @Override
    public void removeFavoriteSet(String removeName) {
        favoriteSet.remove(removeName);
        Utils.setPreference(this, PREF_FAVORITE, favoriteSet);
    }
}
