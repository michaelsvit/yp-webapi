package com.michaelsvit.yesplanet;

import android.util.Log;

/**
 * Created by Michael on 4/9/2017.
 *
 * Object representing Yes Planet cinema
 */

public class Cinema {
    private final String dataUrl = "http://yesplanet.internet-bee.mobi/" +
            "TREST_YP3/resources/info/merge/0/CATS,FEAT,TIX,PRSNT";

    public String getDataUrl() {
        return dataUrl;
    }

    public void parseData(String dataString) {
        Log.v("Cinema parseData", dataString);
    }
}
