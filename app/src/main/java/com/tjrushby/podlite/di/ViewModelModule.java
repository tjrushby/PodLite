package com.tjrushby.podlite.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.tjrushby.podlite.ViewModelFactory;
import com.tjrushby.podlite.podcasts.PodcastsViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(PodcastsViewModel.class)
    abstract ViewModel bindPodcastsViewModel(PodcastsViewModel podcastsViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
