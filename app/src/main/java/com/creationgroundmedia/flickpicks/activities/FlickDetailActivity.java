package com.creationgroundmedia.flickpicks.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.creationgroundmedia.flickpicks.R;
import com.creationgroundmedia.flickpicks.models.Movie;
import com.creationgroundmedia.flickpicks.util.DisplayImage;


public class FlickDetailActivity extends AppCompatActivity {

    private Movie mMovie;

    public static void launchInstance(Context context,
                                      String backdropPath,
                                      String id,
                                      String originalTitle,
                                      String overView,
                                      String popularity,
                                      String posterPath,
                                      String voteAverage) {
        context.startActivity(
                instanceIntent(context,
                        backdropPath,
                        id,
                        originalTitle,
                        overView,
                        popularity,
                        posterPath,
                        voteAverage));
    }

    private static Intent instanceIntent(Context context,
                                         String backdropPath,
                                         String id,
                                         String originalTitle,
                                         String overView,
                                         String popularity,
                                         String posterPath,
                                         String voteAverage) {
        Intent intent = new Intent(context, FlickDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("backdrop_path", backdropPath);
        bundle.putString("id", id);
        bundle.putString("original_title", originalTitle);
        bundle.putString("overview", overView);
        bundle.putString("popularity", popularity);
        bundle.putString("poster_path", posterPath);
        bundle.putString("vote_average", voteAverage);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        mMovie = new Movie(extras.getString("backdrop_path"),
                extras.getString("id"),
                extras.getString("original_title"),
                extras.getString("overview"),
                extras.getString("popularity"),
                extras.getString("poster_path"),
                extras.getString("vote_average"));

        setContentView(R.layout.activity_flick_detail);


        ImageView ivBackdrop = (ImageView) findViewById(R.id.iv_detail_backdrop);
        ImageView ivPoster = (ImageView) findViewById(R.id.iv_detail_poster);
        TextView tvTitle = (TextView) findViewById(R.id.tv_detail_title);
        TextView tvVoteAverage = (TextView) findViewById(R.id.tv_detail_vote_average);
        TextView tvOverview = (TextView) findViewById(R.id.tv_detail_overview);

        DisplayImage.show(this, mMovie.getBackdropPath(), ivBackdrop, null, 720);
        DisplayImage.show(this, mMovie.getPosterPath(), ivPoster, null, 342);

        tvTitle.setText(mMovie.getOriginalTitle());
        tvVoteAverage.setText(toTenths(mMovie.getVoteAverage()));
        tvOverview.setText(mMovie.getOverView());
    }

    private CharSequence toTenths(String average) {
        float f = Float.parseFloat(average);
        return String.format("%.1f", f);
    }
}
