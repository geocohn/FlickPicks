package com.creationgroundmedia.flickpicks.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.creationgroundmedia.flickpicks.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by geo on 10/16/16.
 */

public class DisplayImage {
    public static void show(final Context context,
                      String imageURL,
                      final ImageView ivImage,
                      final ProgressBar pbImage,
                      final int imageSize) {
        Picasso.with(context)
                .load(imageURL)
                .error(R.drawable.image_not_found)
                .into(ivImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (pbImage != null)
                            pbImage.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        scaleDrawableInto(context, R.drawable.image_not_found, ivImage, imageSize);
                        if (pbImage != null)
                            pbImage.setVisibility(View.GONE);
                    }
                });
    }

    static void scaleDrawableInto(Context context, int resourceId, ImageView ivImage, int width) {
        Drawable drawable = context.getResources().getDrawable(resourceId);
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
                ivImage.setColorFilter(ContextCompat.getColor(context, R.color.colorText));
            }
        }
    }

}
