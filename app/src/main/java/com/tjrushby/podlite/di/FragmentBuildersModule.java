package com.tjrushby.podlite.di;

import com.tjrushby.podlite.ui.podcastdetails.PodcastDetailsFragment;
import com.tjrushby.podlite.ui.podcasts.PodcastsFragment;
import com.tjrushby.podlite.ui.search.SearchFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract PodcastDetailsFragment contributeDetailsFragment();

    @ContributesAndroidInjector
    abstract PodcastsFragment contributePodcastsFragment();

    @ContributesAndroidInjector
    abstract SearchFragment contributeSearchFragment();
}
