package com.michaelsvit.yesplanet;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Michael on 4/9/2017.
 *
 * Helper methods to fetch data from Yes Planet server.
 */

public abstract class DataFetchUtils {
    private final static String LOG_TAG = DataFetchUtils.class.getSimpleName();

    public static void fetchData(final String url, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(callback);
    }
}
