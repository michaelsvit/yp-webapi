package com.michaelsvit.yesplanet;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Michael on 4/9/2017.
 *
 * Test parsing data received from Yes Planet server.
 */
public class ParseDataAsyncTest {

    @Test
    public void extractMoviesJson() throws Exception {
        String responseString = "}||{{\"Feats\":[{\"n\":\"movieName\"}],\"Sites\":[{\"id\":1702}]}}||{}||{";
        String moviesJson = JsonParseUtils.ParseDataAsync.extractMoviesJson(responseString);
        String expectedResult = "[{\"n\":\"movieName\"}]";
        assertEquals(moviesJson, expectedResult);
    }


}