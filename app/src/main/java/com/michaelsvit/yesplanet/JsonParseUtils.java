package com.michaelsvit.yesplanet;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 4/9/2017.
 *
 * Utilities to parse JSON received from Yes Planet server.
 */

public abstract class JsonParseUtils {

    /**
     * Objects calling parseData(...) must implement this interface to know when parsing is finished.
     */
    public interface OnDataParseCompletionListener{
        void onDataParseCompletion();
    }

    /**
     * Asynchronously parse data received from Yes Planet server.
     * @param data      data string from response
     * @param cinema    cinema object to be populated with the parsed data
     * @param listener  object to be informed when data parsing is completed
     */
    public static void parseData(String data, Cinema cinema, OnDataParseCompletionListener listener) {
        (new ParseDataAsync(cinema, listener)).execute(data);
    }

    /**
     * Async task to parse data received from the server.
     */
    public static class ParseDataAsync extends AsyncTask<String, Void, Void> {

        private static final String LOG_TAG = ParseDataAsync.class.getSimpleName();

        private Cinema cinema;
        private OnDataParseCompletionListener listener;

        public ParseDataAsync(Cinema cinema, OnDataParseCompletionListener listener) {
            this.cinema = cinema;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(String... params) {
            if (params.length == 0) {
                Log.e(LOG_TAG, "No data to parse");
            }

            String dataString = params[0];
            String moviesJson = extractMoviesJson(dataString);
            List<Movie> movies = parseMoviesJson(moviesJson);
            cinema.updateMovies(movies);
            return null;
        }

        /**
         * Extract valid JSON string containing movies data from Yes Planet data server response.
         * @param dataString  data server response
         * @return            valid JSON string
         */
        public static String extractMoviesJson(String dataString) {
            final String beginString = "\"Feats\":";
            final int beginIndex = dataString.indexOf(beginString) + beginString.length();
            final String endString = ",\"Sites\"";
            final int endIndex = dataString.indexOf(endString);
            return dataString.substring(beginIndex, endIndex);
        }

        /**
         * Parses JSON string received from querying server's movie database.
         * Assumes valid JSON.
         * @param json  JSON string to be parsed
         */
        public static List<Movie> parseMoviesJson(String json) {
            List<Movie> movies = new ArrayList<>();

            final String SUB_LANG_KEY = "sub";
            final String IS_3D_KEY = "is3d";
            final String ACTORS_KEY = "acts";
            final String RELEASE_TIMESTAMP_KEY = "ds";
            final String LENGTH_KEY = "len";
            final String HEBREW_NAME_KEY = "n";
            final String ENGLISH_NAME_KEY = "an";
            final String RELEASE_YEAR_KEY = "yr";
            final String COUNTRY_KEY = "c";
            final String ID_KEY = "dc";
            final String DIRECTOR_KEY = "dirs";
            final String AGE_RATING_KEY = "rn";
            final String YOUTUBE_TRAILER_ID = "tr";

            try {
                JSONArray moviesArray = new JSONArray(json);
                for(int i = 0; i < moviesArray.length(); i++) {
                    JSONObject movie = moviesArray.getJSONObject(i);
                    String subtitlesLanguage = movie.getString(SUB_LANG_KEY);
                    boolean is3d = movie.getBoolean(IS_3D_KEY);
                    String actors = movie.getString(ACTORS_KEY);
                    long releaseTimestamp = movie.getLong(RELEASE_TIMESTAMP_KEY);
                    int length = movie.getInt(LENGTH_KEY);
                    String hebrewName = movie.getString(HEBREW_NAME_KEY);
                    String englishName = movie.getString(ENGLISH_NAME_KEY);
                    String releaseYear = movie.getString(RELEASE_YEAR_KEY);
                    String country = movie.getString(COUNTRY_KEY);
                    String id = movie.getString(ID_KEY);
                    String director = movie.getString(DIRECTOR_KEY);
                    String ageRating = movie.getString(AGE_RATING_KEY);
                    String youtubeTrailerId = extractYoutubeTrailerId(movie.getString(YOUTUBE_TRAILER_ID));
                    movies.add(new Movie(
                            subtitlesLanguage,
                            is3d,
                            actors,
                            releaseTimestamp,
                            releaseYear,
                            length,
                            hebrewName,
                            englishName,
                            country,
                            director,
                            id,
                            ageRating,
                            youtubeTrailerId));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "Error parsing JSON string");
            }

            return movies;
        }

        public static String extractYoutubeTrailerId(String string) {
            Uri uri = Uri.parse(string);
            final String videoIdParameter = "v";
            return uri.getQueryParameter(videoIdParameter);
        }
    }
}
