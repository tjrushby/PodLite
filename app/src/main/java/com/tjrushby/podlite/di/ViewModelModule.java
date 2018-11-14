package com.tjrushby.podlite.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.tjrushby.podlite.ViewModelFactory;
import com.tjrushby.podlite.podcasts.PodcastsViewModel;
import com.tjrushby.podlite.search.SearchViewModel;

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
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    abstract ViewModel bindSearchViewModel(SearchViewModel searchViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
