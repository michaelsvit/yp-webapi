package com.michaelsvit.yesplanet;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String LOG_TAG = MovieDetailsActivity.class.getSimpleName();
    public static final String MOVIE_ID_EXTRA = "movie_id_extra";

    private String movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getMovieId();

    }

    private void getMovieId() {
        movieId = getIntent().getStringExtra(MOVIE_ID_EXTRA);
        if (movieId == null) {
            Log.e(LOG_TAG, "No movie ID was received");
        }
    }
}
