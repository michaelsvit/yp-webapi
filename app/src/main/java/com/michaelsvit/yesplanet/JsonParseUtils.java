package com.michaelsvit.yesplanet;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @param listener object to be informed when data parsing is completed
     */
    public static void parseData(String data, OnDataParseCompletionListener listener) {
        (new ParseDataAsync(listener)).execute(data);
    }

    /**
     * Async task to parse data received from the server.
     */
    public static class ParseDataAsync extends AsyncTask<String, Void, Void> {

        private static final String LOG_TAG = ParseDataAsync.class.getSimpleName();

        private List<Movie> movies;

        private OnDataParseCompletionListener listener;

        public ParseDataAsync(OnDataParseCompletionListener listener) {
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
            Map<String, Integer> idToIndexMap = parseMoviesJson(moviesJson);
            String categoriesJson = extractCategoriesJson(dataString);
            updateMovieCategories(categoriesJson, idToIndexMap);
            Cinema.updateMovies(movies);
            return null;
        }

        private void updateMovieCategories(String categoriesJson, Map<String, Integer> idToIndexMap) {
            try {
                JSONArray catArray = new JSONArray(categoriesJson);
                for(int i = 0; i < catArray.length(); i++) {
                    JSONObject category = catArray.getJSONObject(i);

                    final String CATEGORY_MOVIES_KEY = "FC";
                    final String CATEGORY_ID_KEY = "id";

                    Movie.Category movieCategory = Movie.getMovieCategory(category.getInt(CATEGORY_ID_KEY));
                    JSONArray catMoviesArray = category.getJSONArray(CATEGORY_MOVIES_KEY);
                    for(int j = 0; j < catMoviesArray.length(); j++) {
                        String movieId = catMoviesArray.getString(j);
                        final Integer index = idToIndexMap.get(movieId);
                        if (index != null) {
                            Movie movie = movies.get(index);
                            movie.addCategory(movieCategory);
                            Cinema.setCategoryNotEmpty(movieCategory);
                        }
                    }
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error parsing categories data");
            }
        }

        /**
         * Extract valid JSON string containing categories data from Yes Planet data server response.
         * @param dataString data server response
         * @return           valid JSON string
         */
        private String extractCategoriesJson(String dataString) {
            final int beginIndex = 0;
            final String endString = "}||{";
            final int endIndex = dataString.indexOf(endString);
            return dataString.substring(beginIndex, endIndex);
        }

        /**
         * Extract valid JSON string containing movies data from Yes Planet data server response.
         *
         * @param dataString data server response
         * @return           valid JSON string
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
        public Map<String, Integer> parseMoviesJson(String json) {
            movies = new ArrayList<>();
            Map<String, Integer> idToIndexMap = new HashMap<>();

            final String ALT_COUNT_KEY = "ac";
            final String SUB_LANG_KEY = "sub";
            final String IS_3D_KEY = "is3d";
            final String ACTORS_KEY = "acts";
            final String RELEASE_TIMESTAMP_KEY = "ds";
            final String LENGTH_KEY = "len";
            final String HEBREW_TITLE_KEY = "n";
            final String ENGLISH_TITLE_KEY = "an";
            final String RELEASE_YEAR_KEY = "yr";
            final String COUNTRY_KEY = "c";
            final String ID_KEY = "dc";
            final String DIRECTOR_KEY = "dirs";
            final String AGE_RATING_KEY = "rn";
            final String YOUTUBE_TRAILER_ID = "tr";

            try {
                JSONArray moviesArray = new JSONArray(json);
                int savedIndex = 0;
                for (int jsonIndex = 0; jsonIndex < moviesArray.length(); jsonIndex++) {
                    JSONObject movie = moviesArray.getJSONObject(jsonIndex);

                    String englishTitle = safeGetString(ENGLISH_TITLE_KEY, movie);

                    String id;
                    if (movie.has(ID_KEY)) {
                        id = movie.getString(ID_KEY);
                        // If a single movie has a few entries, skip all but the first one
                        if (idToIndexMap.get(id) != null) {
                            continue;
                        }
                    } else {
                        Log.e(LOG_TAG, "Movie with name: " + englishTitle + " has no ID");
                        continue;
                    }

                    String subtitlesLanguage = getSubtitlesLanguage(SUB_LANG_KEY, movie);

                    boolean is3d = movie.has(IS_3D_KEY) && movie.getBoolean(IS_3D_KEY);

                    String actors = safeGetString(ACTORS_KEY, movie);

                    long releaseTimestamp = safeGetReleaseTimestamp(RELEASE_TIMESTAMP_KEY, movie);

                    String releaseDate =
                            (releaseTimestamp != -1) ? getFormattedDate(releaseTimestamp) : "";

                    int length = safeGetLength(LENGTH_KEY, movie);

                    String hebrewTitle = safeGetString(HEBREW_TITLE_KEY, movie);

                    String country = safeGetString(COUNTRY_KEY, movie);

                    String director = safeGetString(DIRECTOR_KEY, movie);

                    String ageRating = safeGetString(AGE_RATING_KEY, movie);

                    String youtubeTrailerId = safeGetYoutubeTrailerId(YOUTUBE_TRAILER_ID, movie);

                    movies.add(savedIndex, new Movie(
                            subtitlesLanguage,
                            is3d,
                            actors,
                            releaseTimestamp,
                            releaseDate,
                            length,
                            hebrewTitle,
                            englishTitle,
                            country,
                            director,
                            id,
                            ageRating,
                            youtubeTrailerId));
                    idToIndexMap.put(id, savedIndex);
                    savedIndex++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "Error parsing JSON string");
            }

            return idToIndexMap;
        }

        private static String getFormattedDate(long unixTimestamp) {
            Date date = new Date(unixTimestamp * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return sdf.format(date);
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
