package com.michaelsvit.yesplanet;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Michael on 4/9/2017.
 *
 * Unit tests for extracting valid JSON strings from data server response.
 */
public class CinemaHelperMethodsTest {
    @Test
    public void extractMoviesJson() throws Exception {
        String responseString = "}||{{\"Feats\":[{\"n\":\"movieName\"}],\"Sites\":[{\"id\":1702}]}}||{}||{";
        String moviesJson = CinemaHelperMethods.extractMoviesJson(responseString);
        String expectedResult = "[{\"n\":\"movieName\"}]";
        assertEquals(moviesJson, expectedResult);
    }

}