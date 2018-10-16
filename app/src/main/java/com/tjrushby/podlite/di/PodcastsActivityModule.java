package com.tjrushby.podlite.di;

import com.tjrushby.podlite.podcasts.PodcastsFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class PodcastsActivityModule {

    @Provides
    PodcastsFragment providePodcastsFragment() {
        return new PodcastsFragment();
    }
}
