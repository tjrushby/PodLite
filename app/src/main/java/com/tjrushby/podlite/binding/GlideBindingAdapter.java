package com.tjrushby.podlite.binding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.tjrushby.podlite.R;

public class GlideBindingAdapter {

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .apply(new RequestOptions().placeholder(R.drawable.ic_image).fitCenter())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view);
    }
}
