package com.creationgroundmedia.flickpicks.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.creationgroundmedia.flickpicks.R;
import com.creationgroundmedia.flickpicks.activities.FlickDetailActivity;
import com.creationgroundmedia.flickpicks.models.Movie;
import com.creationgroundmedia.flickpicks.util.DisplayImage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geo on 10/11/16.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    static final String LOG_TAG = MoviesAdapter.class.getSimpleName();
    private Context mContext;
    private List<Movie> mMovies;

    public Context getContext() {
        return mContext;
    }

    public MoviesAdapter(Context context, ArrayList<Movie> movies) {
        mContext = context;
        mMovies = movies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Normally I don't like one-liners but this one was just too much fun!
        return new ViewHolder(LayoutInflater
                .from(getContext())
                .inflate(R.layout.flick_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movie movie = mMovies.get(position);
        holder.tvTitle.setText(movie.getOriginalTitle());
        holder.tvOverview.setText(movie.getOverView());
        boolean isHorizontal = holder.ivBackdrop != null;
        if (isHorizontal) {
            DisplayImage.show(getContext(), movie.getBackdropPath(), holder.ivBackdrop, holder.pbImage, 720);
        } else {
            DisplayImage.show(getContext(), movie.getPosterPath(), holder.ivPoster, holder.pbImage, 342);
        }
        holder.vItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlickDetailActivity
                        .launchInstance(mContext,
                                movie.getBackdropPath(),
                                movie.getId(),
                                movie.getOriginalTitle(),
                                movie.getOverView(),
                                movie.getPopularity(),
                                movie.getPosterPath(),
                                movie.getVoteAverage());
            }
        });
    }

    private void show(String imageURL, final ImageView ivImage, final ProgressBar pbImage, final int imageSize) {
        Picasso.with(getContext())
                .load(imageURL)
                .error(R.drawable.image_not_found)
                .into(ivImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        pbImage.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        ScaleDrawableInto(R.drawable.image_not_found, ivImage, imageSize);
                        pbImage.setVisibility(View.GONE);
                    }
                });
    }

    private void ScaleDrawableInto(int resourceId, ImageView ivImage, int width) {
        Drawable drawable = getContext().getResources().getDrawable(resourceId);
        Bitmap bitmap;
        boolean isVector = !(drawable instanceof BitmapDrawable);

        if (isVector) {
            int height = (width * drawable.getIntrinsicHeight()) / drawable.getIntrinsicWidth();
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        } else {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            bitmap = bitmapDrawable.getBitmap();
        }

        if (bitmap != null) {
            ivImage.setImageBitmap(bitmap);
            if (isVector) {
                ivImage.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorText));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View vItem;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        ImageView ivBackdrop;
        ProgressBar pbImage;

        public ViewHolder(View itemView) {
            super(itemView);

            vItem = itemView;
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            ivPoster = (ImageView) itemView.findViewById(R.id.ivPoster);
            ivBackdrop = (ImageView) itemView.findViewById(R.id.ivBackdrop);
            pbImage = (ProgressBar) itemView.findViewById(R.id.pbImage);
        }
    }
}
