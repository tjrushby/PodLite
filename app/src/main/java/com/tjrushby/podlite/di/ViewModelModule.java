package com.tjrushby.podlite.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.tjrushby.podlite.ViewModelFactory;
import com.tjrushby.podlite.ui.podcastdetails.PodcastDetailsViewModel;
import com.tjrushby.podlite.ui.podcasts.PodcastsViewModel;
import com.tjrushby.podlite.ui.search.SearchViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(PodcastDetailsViewModel.class)
    abstract ViewModel bindDetailsViewModel(PodcastDetailsViewModel podcastDetailsViewModel);

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
