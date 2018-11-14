package com.tjrushby.podlite.search;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchActivityModule {

    @Provides
    SearchFragment provideSearchFragment() {
        return new SearchFragment();
    }
}
