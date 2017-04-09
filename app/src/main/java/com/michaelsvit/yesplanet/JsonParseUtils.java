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
 * <p>
 * Utilities to parse JSON received from Yes Planet server.
 */

public abstract class JsonParseUtils {

    /**
     * Objects calling parseData(...) must implement this interface to know when parsing is finished.
     */
    public interface OnDataParseCompletionListener {
        void onDataParseCompletion();
    }

    /**
     * Asynchronously parse data received from Yes Planet server.
     *
     * @param data     data string from response
     * @param cinema   cinema object to be populated with the parsed data
     * @param listener object to be informed when data parsing is completed
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
        protected void onPostExecute(Void aVoid) {
            listener.onDataParseCompletion();
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
         *
         * @param dataString data server response
         * @return valid JSON string
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
         *
         * @param json JSON string to be parsed
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
                for (int i = 0; i < moviesArray.length(); i++) {
                    JSONObject movie = moviesArray.getJSONObject(i);

                    String subtitlesLanguage = getSubtitlesLanguage(SUB_LANG_KEY, movie);

                    boolean is3d = movie.has(IS_3D_KEY) && movie.getBoolean(IS_3D_KEY);

                    String actors = safeGetString(ACTORS_KEY, movie);

                    long releaseTimestamp = safeGetReleaseTimestamp(RELEASE_TIMESTAMP_KEY, movie);

                    int length = safeGetLength(LENGTH_KEY, movie);

                    String hebrewName = safeGetString(HEBREW_NAME_KEY, movie);

                    String englishName = safeGetString(ENGLISH_NAME_KEY, movie);

                    String releaseYear = safeGetString(RELEASE_YEAR_KEY, movie);

                    String country = safeGetString(COUNTRY_KEY, movie);

                    String id;
                    if (movie.has(ID_KEY)) {
                        id = movie.getString(ID_KEY);
                    } else {
                        Log.e(LOG_TAG, "Movie with name: " + englishName + " has no ID");
                        continue;
                    }

                    String director = safeGetString(DIRECTOR_KEY, movie);

                    String ageRating = safeGetString(AGE_RATING_KEY, movie);

                    String youtubeTrailerId = safeGetYoutubeTrailerId(YOUTUBE_TRAILER_ID, movie);

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

        private static int safeGetLength(String LENGTH_KEY, JSONObject movie) throws JSONException {
            int length;
            if (movie.has(LENGTH_KEY)) {
                length = movie.getInt(LENGTH_KEY);
            } else {
                length = -1;
            }
            return length;
        }

        private static long safeGetReleaseTimestamp(String RELEASE_TIMESTAMP_KEY, JSONObject movie) throws JSONException {
            long releaseTimestamp;
            if (movie.has(RELEASE_TIMESTAMP_KEY)) {
                releaseTimestamp = movie.getLong(RELEASE_TIMESTAMP_KEY);
            } else {
                releaseTimestamp = -1;
            }
            return releaseTimestamp;
        }

        private static String safeGetYoutubeTrailerId(String YOUTUBE_TRAILER_ID, JSONObject movie) throws JSONException {
            String youtubeTrailerId;
            if (movie.has(YOUTUBE_TRAILER_ID)) {
                youtubeTrailerId = extractYoutubeTrailerId(movie.getString(YOUTUBE_TRAILER_ID));
            } else {
                youtubeTrailerId = "";
            }
            return youtubeTrailerId;
        }

        private static String safeGetString(String ACTORS_KEY, JSONObject movie) throws JSONException {
            String actors;
            if (movie.has(ACTORS_KEY)) {
                actors = movie.getString(ACTORS_KEY);
            } else {
                actors = "";
            }
            return actors;
        }

        private static String getSubtitlesLanguage(String SUB_LANG_KEY, JSONObject movie) throws JSONException {
            String subtitlesLanguage;
            if (movie.has(SUB_LANG_KEY)) {
                subtitlesLanguage = movie.getString(SUB_LANG_KEY);
            } else {
                subtitlesLanguage = "Hebrew";
            }
            return subtitlesLanguage;
        }

        public static String extractYoutubeTrailerId(String string) {
            Uri uri = Uri.parse(string);
            final String videoIdParameter = "v";
            return uri.getQueryParameter(videoIdParameter);
        }
    }
}
