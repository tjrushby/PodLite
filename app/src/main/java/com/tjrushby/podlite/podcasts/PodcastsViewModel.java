package com.tjrushby.podlite.podcasts;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import com.tjrushby.podlite.data.source.PodcastsRepository;

import javax.inject.Inject;

public class PodcastsViewModel extends AndroidViewModel {

    private final Context context; // use Application Context to avoid leaks

    private final PodcastsRepository podcastsRepo;

    public PodcastsViewModel(@NonNull Application application, PodcastsRepository podcastsRepo) {
        super(application);

        context = application.getApplicationContext();
        this.podcastsRepo = podcastsRepo;
    }

    public void addNewPodcast() {

    }
}
