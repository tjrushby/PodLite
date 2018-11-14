package com.tjrushby.podlite.di;

import com.tjrushby.podlite.podcasts.PodcastsActivity;
import com.tjrushby.podlite.podcasts.PodcastsActivityModule;
import com.tjrushby.podlite.search.SearchActivity;
import com.tjrushby.podlite.search.SearchActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = PodcastsActivityModule.class)
    abstract PodcastsActivity contributePodcastsActivityInjector();

    @ActivityScope
    @ContributesAndroidInjector(modules = SearchActivityModule.class)
    abstract SearchActivity contributeSearchActivityInjector();
}
