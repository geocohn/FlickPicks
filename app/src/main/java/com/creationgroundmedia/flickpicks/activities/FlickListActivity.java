package com.creationgroundmedia.flickpicks.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.creationgroundmedia.flickpicks.R;
import com.creationgroundmedia.flickpicks.adapters.MoviesAdapter;
import com.creationgroundmedia.flickpicks.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class FlickListActivity extends AppCompatActivity {

    ArrayList<Movie> mMovies;
    private MoviesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flick_list);

        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults = null;

                try {
                    movieJsonResults = response.getJSONArray("results");
                    mMovies.addAll(Movie.fromJsonArray(movieJsonResults));
                    mAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

        RecyclerView rvMovies = (RecyclerView) findViewById(R.id.rvMovies);
        mMovies = new ArrayList<>();
        mAdapter = new MoviesAdapter(this, mMovies);
        rvMovies.setAdapter(mAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
    }
}
