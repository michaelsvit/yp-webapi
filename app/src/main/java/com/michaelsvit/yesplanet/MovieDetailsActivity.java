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

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
        length.setText(String.valueOf(movie.getLength()));

        TextView releaseDate = (TextView) findViewById(R.id.movie_details_release_date);
        releaseDate.setText(movie.getReleaseDate());

        TextView director = (TextView) findViewById(R.id.movie_details_director);
        director.setText(movie.getDirector());

        TextView categories = (TextView) findViewById(R.id.movie_details_categories);
        // TODO: update categories

        final TextView synopsis = (TextView) findViewById(R.id.movie_details_synopsis);
        updateSynopsis(synopsis);

        TextView actors = (TextView) findViewById(R.id.movie_details_actors);
        actors.setText(movie.getActors());
    }

    private void updateSynopsis(final TextView synopsis) {
        DataFetchUtils.fetchData(Cinema.getSynopsisUrl(movie.getId()), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "Error fetching synopsis from URL: " + call.request().url());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException();
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            synopsis.setText(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e(LOG_TAG, "Error updating synopsis text with request result");
                        }
                    }
                });
            }
        });
    }

    private void getMovie() {
        movie = getIntent().getParcelableExtra(MOVIE_EXTRA);
        if (movie == null) {
            Log.e(LOG_TAG, "No movie was received");
        }
    }
}
