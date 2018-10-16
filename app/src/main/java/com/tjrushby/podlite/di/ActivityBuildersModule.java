package com.tjrushby.podlite.di;

import com.tjrushby.podlite.podcasts.PodcastsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = PodcastsActivityModule.class)
    abstract PodcastsActivity contributePodcastsActivityInjector();
}
