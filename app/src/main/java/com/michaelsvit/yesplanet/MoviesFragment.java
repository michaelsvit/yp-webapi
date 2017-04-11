package com.michaelsvit.yesplanet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * Fragment containing a list of movie cards.
 */
public class MoviesFragment extends Fragment {

    private MoviesAdapter adapter;
    private List<Movie> movies;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_movies, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.movies_recycler_view);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        movies = Cinema.getMovies();
        adapter = new MoviesAdapter(movies, getActivity());
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }
}
