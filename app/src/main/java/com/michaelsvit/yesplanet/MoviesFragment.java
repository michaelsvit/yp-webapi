package com.michaelsvit.yesplanet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import static com.michaelsvit.yesplanet.MovieDetailsActivity.LOG_TAG;


/**
 * Fragment containing a list of movie cards.
 */
public class MoviesFragment extends Fragment {

    private MoviesAdapter adapter;
    private List<Movie> movies;
    private String filter;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment_movies, menu);
        for (Movie.Category category : Movie.Category.values()) {
            if (Cinema.isCategoryEmpty(category)) {
                menu.findItem(getCategoryMenuResId(category)).setVisible(false);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter_all:
                filter = "";
                break;
            case R.id.action_filter_drama:
                filter = Movie.Category.DRAMA.name();
                break;
            case R.id.action_filter_thriller:
                filter = Movie.Category.THRILLER.name();
                break;
            case R.id.action_filter_action:
                filter = Movie.Category.ACTION.name();
                break;
            case R.id.action_filter_comedy:
                filter = Movie.Category.COMEDY.name();
                break;
            case R.id.action_filter_kids:
                filter = Movie.Category.KIDS.name();
                break;
            case R.id.action_filter_kids_show:
                filter = Movie.Category.KIDS_SHOW.name();
                break;
            case R.id.action_filter_kids_club:
                filter = Movie.Category.KIDS_CLUB.name();
                break;
            case R.id.action_filter_morning_events:
                filter = Movie.Category.MORNING_EVENTS.name();
                break;
            case R.id.action_filter_opera:
                filter = Movie.Category.OPERA.name();
                break;
            case R.id.action_filter_classic:
                filter = Movie.Category.CLASSIC.name();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        adapter.getFilter().filter(filter);
        return true;
    }

    private int getCategoryMenuResId(Movie.Category category) {
        switch (category) {
            case DRAMA:
                return R.id.action_filter_drama;
            case THRILLER:
                return R.id.action_filter_thriller;
            case ACTION:
                return R.id.action_filter_action;
            case COMEDY:
                return R.id.action_filter_comedy;
            case KIDS:
                return R.id.action_filter_kids;
            case KIDS_SHOW:
                return R.id.action_filter_kids_show;
            case KIDS_CLUB:
                return R.id.action_filter_kids_club;
            case MORNING_EVENTS:
                return R.id.action_filter_morning_events;
            case OPERA:
                return R.id.action_filter_opera;
            case CLASSIC:
                return R.id.action_filter_classic;
            default:
                Log.e(LOG_TAG, "Unrecognized category: " + category);
                return -1;
        }
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
        filter = "";
        adapter.getFilter().filter(filter);

        return rootView;
    }

}
