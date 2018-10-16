package com.tjrushby.podlite.di;

import com.tjrushby.podlite.podcasts.PodcastsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract PodcastsFragment contributePodcastsFragment();
}
