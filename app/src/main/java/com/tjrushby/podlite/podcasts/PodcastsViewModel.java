package com.tjrushby.podlite.podcasts;

import android.arch.lifecycle.ViewModel;

import com.tjrushby.podlite.data.source.PodcastsRepository;

import javax.inject.Inject;

import timber.log.Timber;

public class PodcastsViewModel extends ViewModel {

    private final PodcastsRepository podcastsRepo;

    @Inject
    public PodcastsViewModel(PodcastsRepository podcastsRepo) {
        this.podcastsRepo = podcastsRepo;
    }

    public void addNewPodcast() {
        Timber.d("addNewPodcast()");
    }
}
