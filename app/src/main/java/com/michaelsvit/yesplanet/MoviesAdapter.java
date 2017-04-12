package com.michaelsvit.yesplanet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Michael on 4/10/2017.
 * Adapter for the RecyclerView with movie cards.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<Movie> movies;
    private Context context;

    public MoviesAdapter(List<Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);

        return new ViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        Glide.with(context).load(Cinema.getMoviePosterUrl(movie.getId())).into(holder.poster);
        holder.title.setText(movie.getHebrewTitle());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView poster;
        private TextView title;

        private Context context;

        public ViewHolder(View itemView, Context context) {
            super(itemView);

            poster = (ImageView) itemView.findViewById(R.id.movie_details_poster);
            title = (TextView) itemView.findViewById(R.id.title);
            this.context = context;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra(MovieDetailsActivity.MOVIE_EXTRA, Cinema.getMovie(getAdapterPosition()));
            context.startActivity(intent);
        }
    }
}
