package com.michaelsvit.yesplanet;

/**
 * Created by Michael on 4/9/2017.
 *
 * Helper methods for Cinema object
 */

public abstract class CinemaHelperMethods {

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
}
