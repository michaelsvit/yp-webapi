package com.michaelsvit.yesplanet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 4/10/2017.
 * Adapter for the RecyclerView with movie cards.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> implements Filterable {

    private List<Movie> unfilteredMovies;
    private List<Movie> filteredMovies;
    private Context context;

    private MoviesFilter moviesFilter;

    public MoviesAdapter(List<Movie> unfilteredMovies, Context context) {
        this.unfilteredMovies = unfilteredMovies;
        this.filteredMovies = new ArrayList<>();
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
        Movie movie = filteredMovies.get(position);
        holder.setMovie(movie);
        Glide.with(context).load(Cinema.getMoviePosterUrl(movie.getId())).into(holder.poster);
        holder.title.setText(movie.getHebrewTitle());
    }

    @Override
    public int getItemCount() {
        return filteredMovies.size();
    }

    @Override
    public Filter getFilter() {
        if (moviesFilter == null) {
            moviesFilter = new MoviesFilter(this, unfilteredMovies);
        }
        return moviesFilter;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView poster;
        private TextView title;

        private Context context;
        private Movie movie;

        public ViewHolder(View itemView, Context context) {
            super(itemView);

            poster = (ImageView) itemView.findViewById(R.id.movie_details_poster);
            title = (TextView) itemView.findViewById(R.id.title);
            this.context = context;
            itemView.setOnClickListener(this);
        }

        private void setMovie(Movie movie) {
            this.movie = movie;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra(MovieDetailsActivity.MOVIE_EXTRA, movie);
            context.startActivity(intent);
        }
    }

    private static class MoviesFilter extends Filter {

        private final MoviesAdapter adapter;
        private final List<Movie> unfilteredMovies;
        private final List<Movie> filteredMovies;

        public MoviesFilter(MoviesAdapter adapter, List<Movie> unfilteredMovies) {
            super();
            this.adapter = adapter;
            this.unfilteredMovies = unfilteredMovies;
            this.filteredMovies = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredMovies.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                filteredMovies.addAll(unfilteredMovies);
            } else {
                Movie.Category category =
                        Movie.getMovieCategory(Integer.valueOf(constraint.toString()));
                for (Movie movie : unfilteredMovies) {
                    if (movie.getCategories().contains(category)) {
                        filteredMovies.add(movie);
                    }
                }
            }

            results.values = filteredMovies;
            results.count = filteredMovies.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.filteredMovies.clear();
            adapter.filteredMovies.addAll((List<Movie>) results.values);
            adapter.notifyDataSetChanged();
        }
    }
}
