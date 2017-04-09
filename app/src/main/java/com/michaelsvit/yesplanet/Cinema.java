package com.michaelsvit.yesplanet;

import java.util.List;

/**
 * Created by Michael on 4/9/2017.
 *
 * Object representing Yes Planet cinema
 */

public class Cinema {

    private final static String LOG_TAG = Cinema.class.getSimpleName();

    private List<Movie> movies;

    public String getDataUrl() {
        return "http://yesplanet.internet-bee.mobi/" +
                "TREST_YP3/resources/info/merge/0/CATS,FEAT,TIX,PRSNT";
    }

    public void updateMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public String getMoviePosterUrl(String movieId) {
        return "http://media3.cinema-city.pl/yp2/Feats/med/" + movieId + ".jpg";
    }
}
