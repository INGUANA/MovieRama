package com.inguana.movierama.utils;

import static com.inguana.movierama.utils.Constants.PREFS_NAME;

import android.content.Context;
import android.content.SharedPreferences;

import com.inguana.movierama.models.Cast;
import com.inguana.movierama.models.Crew;
import com.inguana.movierama.models.Genre;
import com.inguana.movierama.models.ImageConfigurations;
import com.inguana.movierama.models.Movie;
import com.inguana.movierama.models.Review;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Utils {

    public static String buildImageBaseUrl(ImageConfigurations imageConfigurations) {

        String baseUrl = "";
        if(imageConfigurations != null) {
             baseUrl = imageConfigurations.getSecure_base_url();

            ArrayList<String> posterSizeArrayList = new ArrayList(Arrays.asList(imageConfigurations.getPoster_sizes()));
            if (posterSizeArrayList.size() > 0) {
                int posterSizePosition = Math.round((float) posterSizeArrayList.size() / 2);
                baseUrl += posterSizeArrayList.get(posterSizePosition);
            }
        }

        //TODO:Handle case where there is no internet and we cant get configurations from it

        return baseUrl;
    }

    public static String addNumberToString(String originalNumber, int addAmount) {
        String result;
        try {
            int number = Integer.parseInt(originalNumber);
            result = String.valueOf(number + addAmount);
        } catch (NumberFormatException e) {
            result = "";
        }
        return result;
    }

    public static List<String> extractImageUrls(List<Movie> similarMovieList) {
        List<String> imageUrlList = new ArrayList<>();

        if (similarMovieList != null && similarMovieList.size() > 0) {
            for (Movie movie : similarMovieList) {
                imageUrlList.add(movie.getPoster_path());
            }
        }

        return imageUrlList;
    }

    public static void trimReviews(List<Review> reviewList, int number) {

        if (reviewList != null && reviewList.size() > number && number >= 0) {
            reviewList.subList(number, reviewList.size()).clear();
        }
    }

    public static String getGenresAsString(List<Genre> genreList) {
        String result = "";

        if (genreList != null && genreList.size() > 0) {
            for (Genre genre : genreList) {
                if(genre != null && genre.getName() != null) {
                    result += genre.getName() != null ? genre.getName() + ", " : "";
                }
            }
            result = result.length() < 2 ? "" : result.substring(0, result.length() - 2);
        }

        return result;
    }

    public static String getCastAsString(List<Cast> castList) {
        String result = "";

        if (castList != null && castList.size() > 0) {
            for (Cast cast : castList) {
                if(cast != null && cast.getName() != null) {
                    result += cast.getName() != null ? cast.getName() + ", " : "";
                }
            }
            result = result.length() < 2 ? "" : result.substring(0, result.length() - 2);
        }

        return result;
    }

    public static String getDirectorFromCrew(List<Crew> crewList) {
        String result = "";
        if(crewList != null && crewList.size() > 0) {
            for (Crew crew : crewList) {
                if (crew != null && crew.getJob() != null && crew.getJob().equals("Director")) {
                    result = crew.getName() != null ? crew.getName() : "";
                    break;
                }
            }
        }
        return result;
    }

    public static String changeDateFormatting(String dateString) {
        String result = "";
        if (dateString != null && !dateString.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate dateTime = LocalDate.parse(dateString, formatter);
                DateTimeFormatter finalFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
                result = dateTime.format(finalFormatter);
            } catch (DateTimeParseException e) {
                result = "";
            }
        }
        return result;
    }

    public static void setPreference(Context context, String key, Set<String> value) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    public static Set<String> getPreference(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        return settings.getStringSet(key, new HashSet<String>());
    }

    public static boolean isFavorite(Set<String> favoriteSet, String movieName) {
        boolean result;
        if(favoriteSet != null && ! favoriteSet.isEmpty()) {
            long count = favoriteSet.stream()
                    .filter(favorite -> favorite.equals(movieName))
                    .count();
            result = count == 1;
        } else {
            result = false;
        }
        return result;
    }
}
