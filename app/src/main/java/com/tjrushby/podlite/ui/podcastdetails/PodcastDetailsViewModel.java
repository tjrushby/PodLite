package com.tjrushby.podlite.ui.podcastdetails;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.tjrushby.podlite.data.Podcast;

import javax.inject.Inject;


public class PodcastDetailsViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private final MutableLiveData<Podcast> podcast = new MutableLiveData<>();

    @Inject
    public PodcastDetailsViewModel() {
    }

    LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    LiveData<Podcast> getPodcast() {
        return podcast;
    }
}
