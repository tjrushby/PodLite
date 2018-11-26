package com.tjrushby.podlite.ui.common;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tjrushby.podlite.R;
import com.tjrushby.podlite.data.Podcast;
import com.tjrushby.podlite.databinding.PodcastCardBinding;

public class PodcastAdapter extends DataBoundAdapter<Podcast, PodcastCardBinding> {
    private final DataBindingComponent dataBindingComponent;

    public PodcastAdapter(DataBindingComponent dataBindingComponent) {
        this.dataBindingComponent = dataBindingComponent;
    }

    @Override
    protected PodcastCardBinding createBinding(ViewGroup parent) {
        PodcastCardBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.podcast_card,
                        parent, false, dataBindingComponent);

        return binding;
    }

    @Override
    protected void bind(PodcastCardBinding binding, Podcast item) {
        binding.setPodcast(item);
    }
}
