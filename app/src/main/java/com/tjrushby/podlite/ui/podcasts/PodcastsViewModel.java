package com.tjrushby.podlite.ui.podcasts;

import android.arch.lifecycle.ViewModel;

import com.tjrushby.podlite.SingleLiveEvent;
import com.tjrushby.podlite.data.source.PodcastsRepository;

import javax.inject.Inject;

public class PodcastsViewModel extends ViewModel {

    private final PodcastsRepository podcastsRepo;

    private final SingleLiveEvent<Void> searchEvent = new SingleLiveEvent<>();

    @Inject
    public PodcastsViewModel(PodcastsRepository podcastsRepo) {
        this.podcastsRepo = podcastsRepo;
    }

    void searchPodcasts() {
        searchEvent.call();
    }

    SingleLiveEvent<Void> getSearchEvent() {
        return searchEvent;
    }
}
