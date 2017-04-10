package com.michaelsvit.yesplanet;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Michael on 4/9/2017.
 *
 * Instrumented tests for parsing JSON data.
 */
@RunWith(AndroidJUnit4.class)
public class ParseDataAsyncInstrumentedTest {

    @Test
    public void parseMoviesJson() throws Exception {
        String moviesJson = "[{\"sub\":\"Hebrew\"," +
                "\"is3d\":false," +
                "\"acts\":\"John Doe\"," +
                "\"ds\":123456789," +
                "\"len\":120," +
                "\"yr\":\"2017\"," +
                "\"c\":\"Israel\"," +
                "\"dirs\":\"Michael S.\"," +
                "\"an\":\"Movie1\"," +
                "\"n\":\"סרט1\"," +
                "\"rn\":\"Age 18+\"," +
                "\"tr\":\"https://www.youtube.com/watch?v=youtubeId1&feature=youtu.be\"," +
                "\"dc\":\"1234rtyu\"}]";
        List<Movie> parsedMovies = JsonParseUtils.ParseDataAsync.parseMoviesJson(moviesJson);
        Movie parsedMovie = parsedMovies.get(0);
        Movie expectedMovie = new Movie(
                "Hebrew",
                false,
                "John Doe",
                123456789,
                "2017",
                120,
                "סרט1",
                "Movie1",
                "Israel",
                "Michael S.",
                "1234rtyu",
                "Age 18+",
                "youtubeId1");

        assertEquals(parsedMovie.getActors(), expectedMovie.getActors());
        assertEquals(parsedMovie.getAgeRating(), expectedMovie.getAgeRating());
        assertEquals(parsedMovie.getCountry(), expectedMovie.getCountry());
        assertEquals(parsedMovie.getDirector(), expectedMovie.getDirector());
        assertEquals(parsedMovie.getEnglishTitle(), expectedMovie.getEnglishTitle());
        assertEquals(parsedMovie.getHebrewTitle(), expectedMovie.getHebrewTitle());
        assertEquals(parsedMovie.getId(), expectedMovie.getId());
        assertEquals(parsedMovie.getLength(), expectedMovie.getLength());
        assertEquals(parsedMovie.getReleaseTimestamp(), expectedMovie.getReleaseTimestamp());
        assertEquals(parsedMovie.getReleaseYear(), expectedMovie.getReleaseYear());
        assertEquals(parsedMovie.getSubtitlesLanguage(), expectedMovie.getSubtitlesLanguage());
        assertEquals(parsedMovie.getYoutubeTrailerId(), expectedMovie.getYoutubeTrailerId());
    }


}