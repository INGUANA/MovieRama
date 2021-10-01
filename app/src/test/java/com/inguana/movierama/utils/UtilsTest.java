package com.inguana.movierama.utils;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.content.SharedPreferences;

import com.inguana.movierama.models.Cast;
import com.inguana.movierama.models.Crew;
import com.inguana.movierama.models.Genre;
import com.inguana.movierama.models.ImageConfigurations;
import com.inguana.movierama.models.Movie;
import com.inguana.movierama.models.Review;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UtilsTest {

    //====================================================
    //TODO: Enhance with Mockito
    //====================================================
    /*
    @Mock
    private Context mockApplicationContext;

    @Mock
    private SharedPreferences mockSharedPreferences;
    @Mock
    private SharedPreferences.Editor mockEditor;

    @Before
    public void setUpTests() {
        when(mockApplicationContext.getSharedPreferences(anyString(), anyInt())).thenReturn(mockSharedPreferences);
        when(mockSharedPreferences.edit()).thenReturn(mockEditor);

    }*/

    @Test
    public void checkBuildImageBaseUrl() {

        String baseUrl = "https://baseurl.com/";
        String size = "500px";
        ImageConfigurations imageConfigurations = new ImageConfigurations();
        imageConfigurations.setSecure_base_url(baseUrl);

        String[] sizeArray = new String[]{"300px", "400px", "500px", "600px"};
        imageConfigurations.setPoster_sizes(sizeArray);
        assertEquals(Utils.buildImageBaseUrl(imageConfigurations), baseUrl + size);
    }

    @Test
    public void checkBuildImageBaseUrl_zeroSize() {

        String baseUrl = "https://baseurl.com/";
        String size = "";
        ImageConfigurations imageConfigurations = new ImageConfigurations();
        imageConfigurations.setSecure_base_url(baseUrl);

        String[] sizeArray = new String[]{};
        imageConfigurations.setPoster_sizes(sizeArray);
        assertEquals(Utils.buildImageBaseUrl(imageConfigurations), baseUrl + size);
    }

    @Test
    public void checkBuildImageBaseUrl_nullParameter() {

        String baseUrl = "";
        assertEquals(Utils.buildImageBaseUrl(null), baseUrl);
    }

    @Test(expected = NullPointerException.class)
    public void checkBuildImageBaseUrl_nullField() {

        String baseUrl = "https://baseurl.com/";
        String size = "";
        ImageConfigurations imageConfigurations = new ImageConfigurations();
        imageConfigurations.setSecure_base_url(baseUrl);

        imageConfigurations.setPoster_sizes(null);
        assertEquals(Utils.buildImageBaseUrl(imageConfigurations), baseUrl + size);
    }

    @Test
    public void checkAddNumberToString() {
        String originalNumber = "1";
        int addAmount = 1;

        assertEquals(Utils.addNumberToString(originalNumber, addAmount), "2");
    }

    @Test
    public void checkAddNumberToString_addNegativeNumber() {
        String originalNumber = "1";
        int addAmount = -10;

        assertEquals(Utils.addNumberToString(originalNumber, addAmount), "-9");
    }

    @Test
    public void checkAddNumberToString_nullParameter() {
        int addAmount = -10;

        assertEquals(Utils.addNumberToString(null, addAmount), "");
    }

    @Test
    public void checkAddNumberToString_NaNParameter() {
        String originalNumber = "test";
        int addAmount = -10;

        assertEquals(Utils.addNumberToString(null, addAmount), "");
    }

    @Test
    public void checkAddNumberToString_maxNumber() {
        String originalNumber = "test";
        int addAmount = Integer.MAX_VALUE;

        assertEquals(Utils.addNumberToString(null, addAmount), "");
    }

    @Test
    public void checkExtractImageUrls() {
        List<Movie> similarMovieList = new ArrayList<>();
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Movie movie = new Movie();
            String expectedString = "path:" + i;
            movie.setPoster_path(expectedString);

            similarMovieList.add(movie);
            resultList.add(expectedString);
        }

        assertEquals(Utils.extractImageUrls(similarMovieList), resultList);
    }

    @Test
    public void checkExtractImageUrls_zeroSize() {
        List<Movie> similarMovieList = new ArrayList<>();
        List<String> resultList = new ArrayList<>();

        assertEquals(Utils.extractImageUrls(similarMovieList), resultList);
    }

    @Test
    public void checkExtractImageUrls_nullParameter() {
        List<String> resultList = new ArrayList<>();

        assertEquals(Utils.extractImageUrls(null), resultList);
    }

    @Test
    public void checkExtractImageUrls_nullField() {
        List<Movie> similarMovieList = new ArrayList<>();
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Movie movie = new Movie();
            String expectedString = "path:" + i;
            movie.setPoster_path(null);

            similarMovieList.add(movie);
            resultList.add(null);
        }

        assertEquals(Utils.extractImageUrls(similarMovieList), resultList);
    }

    @Test
    public void checkTrimReviews() {
        List<Review> reviewList = new ArrayList<>();
        List<Review> resultList = new ArrayList<>();
        int number = 2;

        for (int i = 0; i < 5; i++) {
            Review review = new Review("author" + i, "content: " + i);
            reviewList.add(review);
            if (i < number) {
                resultList.add(review);
            }
        }
        Utils.trimReviews(reviewList, number);
        assertEquals(reviewList, resultList);
    }

    @Test
    public void checkTrimReviews_negativeNumber() {
        List<Review> reviewList = new ArrayList<>();
        List<Review> resultList = new ArrayList<>();
        int number = -2;

        for (int i = 0; i < 5; i++) {
            Review review = new Review("author" + i, "content: " + i);
            reviewList.add(review);
            resultList.add(review);

        }
        Utils.trimReviews(reviewList, number);
        assertEquals(reviewList, resultList);
    }

    @Test
    public void checkTrimReviews_numberBiggerThanSize() {
        List<Review> reviewList = new ArrayList<>();
        List<Review> resultList = new ArrayList<>();
        int number = 5;

        for (int i = 0; i < 2; i++) {
            Review review = new Review("author" + i, "content: " + i);
            reviewList.add(review);
            resultList.add(review);

        }
        Utils.trimReviews(reviewList, number);
        assertEquals(reviewList, resultList);
    }

    @Test
    public void checkTrimReviews_null() {
        List<Review> reviewList = null;
        List<Review> resultList = null;
        int number = 5;

        Utils.trimReviews(reviewList, number);
        assertEquals(reviewList, resultList);
    }

    @Test
    public void getGenresAsString() {
        List<Genre> genreList = new ArrayList<>();
        String result = "";
        for (int i = 0; i < 5; i++) {
            Genre genre = new Genre();
            String iterationString = "name: " + i;
            genre.setName(iterationString);
            genreList.add(genre);

            result += iterationString + (i == 4 ? "" : ", ");
        }

        assertEquals(Utils.getGenresAsString(genreList), result);
    }

    @Test
    public void getGenresAsString_null() {
        List<Genre> genreList = null;
        String result = "";

        assertEquals(Utils.getGenresAsString(genreList), result);
    }

    @Test
    public void getGenresAsString_zeroSize() {
        List<Genre> genreList = new ArrayList<>();
        String result = "";

        assertEquals(Utils.getGenresAsString(genreList), result);
    }

    @Test
    public void getGenresAsString_nullField() {
        List<Genre> genreList = new ArrayList<>();
        String result = "";
        for (int i = 0; i < 5; i++) {
            genreList.add(null);
        }

        assertEquals(Utils.getGenresAsString(genreList), result);
    }

    @Test
    public void getGenresAsString_nullFieldName() {
        List<Genre> genreList = new ArrayList<>();
        String result = "";
        for (int i = 0; i < 5; i++) {
            Genre genre = new Genre();
            String iterationString = "name: " + i;
            genre.setName(null);
            genreList.add(genre);
        }

        assertEquals(Utils.getGenresAsString(genreList), result);
    }

    @Test
    public void getCastAsString() {
        List<Cast> castList = new ArrayList<>();
        String result = "";
        for (int i = 0; i < 5; i++) {
            Cast cast = new Cast();
            String iterationString = "name: " + cast;
            cast.setName(iterationString);

            castList.add(cast);

            result += iterationString + (i == 4 ? "" : ", ");
        }

        assertEquals(Utils.getCastAsString(castList), result);
    }

    @Test
    public void getCastAsString_null() {
        List<Cast> castList = null;
        String result = "";

        assertEquals(Utils.getCastAsString(castList), result);
    }

    @Test
    public void getCastAsString_zeroSize() {
        List<Cast> castList = new ArrayList<>();
        String result = "";

        assertEquals(Utils.getCastAsString(castList), result);
    }

    @Test
    public void getCastAsString_nullField() {
        List<Cast> castList = new ArrayList<>();
        String result = "";
        for (int i = 0; i < 5; i++) {
            castList.add(null);
        }

        assertEquals(Utils.getCastAsString(castList), result);
    }

    @Test
    public void getCastAsString_nullFieldName() {
        List<Cast> castList = new ArrayList<>();
        String result = "";
        for (int i = 0; i < 5; i++) {
            Cast cast = new Cast();
            String iterationString = "name: " + cast;
            cast.setName(null);

            castList.add(cast);
        }

        assertEquals(Utils.getCastAsString(castList), result);
    }

    @Test
    public void getDirectorFromCrew() {
        List<Crew> crewList = new ArrayList<>();
        String result = "Joseph";
        for (int i = 0; i < 5; i++) {
            Crew crew = new Crew();
            if (i == 3) {
                crew.setJob("Director");
                crew.setName(result);
            } else {
                crew.setJob("job: " + i);
                crew.setName("name: " + i);
            }
            crewList.add(crew);
        }
        assertEquals(Utils.getDirectorFromCrew(crewList), result);
    }

    @Test
    public void getDirectorFromCrew_null() {
        List<Crew> crewList = new ArrayList<>();
        String result = "";

        assertEquals(Utils.getDirectorFromCrew(crewList), result);
    }

    @Test
    public void getDirectorFromCrew_zeroSize() {
        List<Crew> crewList = new ArrayList<>();
        String result = "";

        assertEquals(Utils.getDirectorFromCrew(crewList), result);
    }

    @Test
    public void getDirectorFromCrew_nullItem() {
        List<Crew> crewList = new ArrayList<>();
        String result = "";
        for (int i = 0; i < 5; i++) {
            crewList.add(null);
        }
        assertEquals(Utils.getDirectorFromCrew(crewList), result);
    }

    @Test
    public void getDirectorFromCrew_nullField() {
        List<Crew> crewList = new ArrayList<>();
        String result = "";
        for (int i = 0; i < 5; i++) {
            Crew crew = new Crew();
            if (i == 3) {
                crew.setJob(null);
                crew.setName(result);
            } else {
                crew.setJob("job: " + i);
                crew.setName("name: " + i);
            }
            crewList.add(crew);
        }
        assertEquals(Utils.getDirectorFromCrew(crewList), result);
    }

    @Test
    public void getDirectorFromCrew_nullFieldName() {
        List<Crew> crewList = new ArrayList<>();
        String result = "";
        for (int i = 0; i < 5; i++) {
            Crew crew = new Crew();
            if (i == 3) {
                crew.setJob("Director");
                crew.setName(null);
            } else {
                crew.setJob("job: " + i);
                crew.setName("name: " + i);
            }
            crewList.add(crew);
        }
        assertEquals(Utils.getDirectorFromCrew(crewList), result);
    }

    //========================================
    //TODO: handle locale test
    //========================================
    /*@Test
    public void changeDateFormatting() {
        String dateString = "2021-09-21";
        String result = "21 Sep 2021";

        assertEquals(Utils.changeDateFormatting(dateString), result);
    }*/

    @Test
    public void changeDateFormatting_null() {
        String result = "";

        assertEquals(Utils.changeDateFormatting(null), result);
    }

    @Test
    public void changeDateFormatting_empty() {
        String dateString = "";
        String result = "";

        assertEquals(Utils.changeDateFormatting(dateString), result);
    }

    @Test
    public void changeDateFormatting_invalidDate() {
        String dateString = "21-9-2021";
        String result = "";

        assertEquals(Utils.changeDateFormatting(dateString), result);
    }

    @Test
    public void setPreference() {
        //TODO: Implement with Mockito
    }

    @Test
    public void getPreference() {
        //TODO: Implement with Mockito
    }

    @Test
    public void checkIsFavorite() {
        Set<String> favoriteSet = new HashSet<>();
        String movieName = "name: 3";

        for (int i = 0; i < 5; i++) {
            favoriteSet.add("name: " + i);
        }

        assertTrue(Utils.isFavorite(favoriteSet, movieName));
    }

    @Test
    public void checkIsFavorite_notExists() {
        Set<String> favoriteSet = new HashSet<>();
        String movieName = "name: 31";

        for (int i = 0; i < 5; i++) {
            favoriteSet.add("name: " + i);
        }

        assertFalse(Utils.isFavorite(favoriteSet, movieName));
    }

    @Test
    public void checkIsFavorite_null() {
        String movieName = "name: 3";

        assertFalse(Utils.isFavorite(null, movieName));
    }

    @Test
    public void checkIsFavorite_Empty() {
        Set<String> favoriteSet = new HashSet<>();
        String movieName = "name: 3";

        assertFalse(Utils.isFavorite(favoriteSet, movieName));
    }
}