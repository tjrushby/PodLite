package com.tjrushby.podlite.ui.podcastdetails;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.tjrushby.podlite.data.Podcast;
import com.tjrushby.podlite.data.PodcastsRepository;

import javax.inject.Inject;


public class PodcastDetailsViewModel extends ViewModel {

    private final MutableLiveData<String> collectionId = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private LiveData<Podcast> podcast;

    @Inject
    public PodcastDetailsViewModel(PodcastsRepository podsRepository) {

    }

    void setCollectionId(String collectionId) {
        this.collectionId.setValue(collectionId);
    }

    LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    LiveData<Podcast> getPodcast() {
        return podcast;
    }
}
