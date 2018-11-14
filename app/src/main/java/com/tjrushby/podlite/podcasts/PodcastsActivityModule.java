package com.tjrushby.podlite.podcasts;

import android.content.Intent;

import com.tjrushby.podlite.podcasts.PodcastsFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class PodcastsActivityModule {

    @Provides
    PodcastsFragment providePodcastsFragment() {
        return new PodcastsFragment();
    }

    @Provides
    Intent provideIntent() {
        return new Intent();
    }

}
