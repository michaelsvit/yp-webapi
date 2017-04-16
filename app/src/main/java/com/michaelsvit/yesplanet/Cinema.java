package com.michaelsvit.yesplanet;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Michael on 4/9/2017.
 *
 * Singleton object representing Yes Planet cinema
 */

public class Cinema {

    private final static String LOG_TAG = Cinema.class.getSimpleName();

    private static List<Movie> movies = new ArrayList<>();
    private static Map<Movie.Category, Boolean> isCategoryEmpty = new EnumMap<>(Movie.Category.class);
    static {
        // Initialize all categories to be empty
        for (Movie.Category category : Movie.Category.values()) {
            isCategoryEmpty.put(category, true);
        }
    }

    // Prevent instantiation by declaring private.
    private Cinema() {
    }

    public static String getDataUrl() {
        return "http://yesplanet.internet-bee.mobi/" +
                "TREST_YP3/resources/info/merge/0/CATS,FEAT,TIX,PRSNT";
    }

    public static void updateMovies(List<Movie> movies) {
        Cinema.movies.addAll(movies);
    }

    public static String getMoviePosterUrl(String movieId) {
        return "http://media3.cinema-city.pl/yp2/Feats/med/" + movieId + ".jpg";
    }

    public static String getSynopsisUrl(String movieId) {
        return "http://yesplanet.internet-bee.mobi/TREST_YP3/resources/info/synopsis/" + movieId;
    }

    public static List<Movie> getMovies() {
        return movies;
    }

    public static Movie getMovie(int position) {
        return movies.get(position);
    }

    public static void setCategoryNotEmpty(Movie.Category category) {
        isCategoryEmpty.put(category, false);
    }

    public static boolean isCategoryEmpty(Movie.Category category) {
        return isCategoryEmpty.get(category);
    }
}
