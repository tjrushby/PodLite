package com.tjrushby.podlite.data;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tjrushby.podlite.api.ApiResponse;
import com.tjrushby.podlite.api.ITunesService;
import com.tjrushby.podlite.api.PodcastsSearchResponse;
import com.tjrushby.podlite.util.AbsentLiveData;
import com.tjrushby.podlite.util.AppExecutors;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

public class PodcastsRepository {

    private final AppExecutors executors;

    private final ITunesService iTunesService;

    private final PodLiteDatabase database;

    private final PodcastsDao podDao;


    @Inject
    public PodcastsRepository(AppExecutors executors, ITunesService iTunesService,
                              PodLiteDatabase database, PodcastsDao podDao) {
        this.executors = executors;
        this.iTunesService = iTunesService;
        this.database = database;
        this.podDao = podDao;
    }

    /*
     * gets list of subscribed podcasts from local data source
     */
    public LiveData<Resource<List<Podcast>>> getPodcasts() {
        return new NetworkBoundResource<List<Podcast>, List<Podcast>>(executors) {
            @NonNull
            @Override
            protected LiveData<List<Podcast>> loadFromDatabase() {
                return podDao.getPodcasts();
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Podcast> data) {
                // only retrieving subscribed podcasts from local source, so return false
                return false;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<Podcast>>> createCall() {
                // not sending a network request, so do nothing
                return null;
            }

            @Override
            protected void saveCallResult(@NonNull List<Podcast> item) {
                // not sending a network request, so do nothing
            }

            @Override
            protected void onFetchFailed() {
                // not sending a network request, so do nothing
                super.onFetchFailed();
            }
        }.asLiveData();
    }

    /*
     * gets specified podcast from the most readily available data source
     */
    public LiveData<Resource<Podcast>> getPodcast(@NonNull final String collectionId) {
        return new NetworkBoundResource<Podcast, Podcast>(executors) {
            @NonNull
            @Override
            protected LiveData<Podcast> loadFromDatabase() {
                return podDao.getPodcastById(collectionId);
            }

            @Override
            protected boolean shouldFetch(@Nullable Podcast data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Podcast>> createCall() {
                return iTunesService.getPodcastByCollectionId(collectionId);
            }

            @Override
            protected void saveCallResult(@NonNull Podcast item) {
                // only saving on a button click, not on successful fetch
            }

            @Override
            protected void onFetchFailed() {
                super.onFetchFailed();
            }
        }.asLiveData();
    }

    public void savePodcast(@NonNull Podcast podcast) {
        podDao.insertPodcast(podcast);
    }

    public void deleteAllPodcasts() {
        podDao.deleteAllPodcasts();
    }

    public void deletePodcast(@NonNull String collectionId) {
       podDao.deletePodcast(collectionId);
    }

    public LiveData<Resource<List<Podcast>>> searchPodcasts(String term) {
        return new NetworkOnlyResource<List<Podcast>, PodcastsSearchResponse>(executors) {
            @Override
            protected List<Podcast> processResult(PodcastsSearchResponse request) {
                if(request.getResults() == null) {
                    return new ArrayList<>();
                } else {
                    return request.getResults();
                }
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PodcastsSearchResponse>> createCall() {
                Timber.d("createCall");
                return iTunesService.getPodcastsByTerm(term);
            }

            @Override
            protected void onFetchFailed() {
                super.onFetchFailed();
            }
        }.asLiveData();
    }
}
