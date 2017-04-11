package com.michaelsvit.yesplanet;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String LOG_TAG = MovieDetailsActivity.class.getSimpleName();
    public static final String MOVIE_EXTRA = "movie_extra";

    private Movie movie;

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

        getMovie();
        updateUi();
    }

    private void updateUi() {
        ImageView poster = (ImageView) findViewById(R.id.movie_details_poster);
        Glide.with(this).load(Cinema.getMoviePosterUrl(movie.getId())).into(poster);

        TextView length = (TextView) findViewById(R.id.movie_details_length);
        length.setText(movie.getLength());

        TextView releaseDate = (TextView) findViewById(R.id.movie_details_release_date);
        releaseDate.setText(movie.getReleaseDate());

        TextView director = (TextView) findViewById(R.id.movie_details_director);
        director.setText(movie.getDirector());

        TextView categories = (TextView) findViewById(R.id.movie_details_categories);
        // TODO: update categories

        TextView synopsis = (TextView) findViewById(R.id.movie_details_synopsis);
        // TODO: update synopsis

        TextView actors = (TextView) findViewById(R.id.movie_details_actors);
        actors.setText(movie.getActors());
    }

    private void getMovie() {
        movie = getIntent().getParcelableExtra(MOVIE_EXTRA);
        if (movie == null) {
            Log.e(LOG_TAG, "No movie was received");
        }
    }
}
