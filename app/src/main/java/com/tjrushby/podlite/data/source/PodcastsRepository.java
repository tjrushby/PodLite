package com.tjrushby.podlite.data.source;

import android.support.annotation.NonNull;

import com.tjrushby.podlite.data.Podcast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class PodcastsRepository implements PodcastsDataSource {

    private final PodcastsDataSource podcastsLocalDataSource;

    private Map<String, Podcast> cachedPodcasts;

    @Inject
    public PodcastsRepository(@NonNull PodcastsDataSource podcastsLocalDataSource) {
        this.podcastsLocalDataSource = podcastsLocalDataSource;
    }

    /*
     * gets podcasts from the cache or local data source depending on which is available.
     * If no sources retrieve data LoadPodcastsCallback#onDataNotAvailable is called
     */
    @Override
    public void getPodcasts(@NonNull final LoadPodcastsCallback callback) {
        // return podcasts from cache if available
        if(cachedPodcasts != null) {
            callback.onPodcastsLoaded(new ArrayList<>(cachedPodcasts.values()));
            return;
        }

        // retrieve data from local storage if available
        podcastsLocalDataSource.getPodcasts(new LoadPodcastsCallback() {
            @Override
            public void onPodcastsLoaded(List<Podcast> podcasts) {
                // successfully retrieved from local data source, update the cache and return
                refreshCache(podcasts);
                callback.onPodcastsLoaded(podcasts);
            }

            @Override
            public void onDataNotAvailable() {
                // data not available in local storage
                callback.onDataNotAvailable();
            }
        });
    }

    /*
     * gets podcast from most readily available data source.
     * If no sources contain podcastId GetPodcastCallback#onDataNotAvailable is called
     */
    @Override
    public void getPodcast(@NonNull final String podcastId,
                           @NonNull final GetPodcastCallback callback) {
        // return podcast from cache if available
        Podcast podcast = getPodcastWithId(podcastId);

        if(podcast != null) {
            callback.onPodcastLoaded(podcast);
            return;
        }

        // check local source for podcast
        podcastsLocalDataSource.getPodcast(podcastId, new GetPodcastCallback() {
            @Override
            public void onPodcastLoaded(Podcast podcast) {
                if(podcast == null) {
                    onDataNotAvailable();
                    return;
                }

                // add podcast to cache
                if(cachedPodcasts == null) {
                    cachedPodcasts = new LinkedHashMap<>();
                }

                cachedPodcasts.put(podcast.getPodId(), podcast);
                callback.onPodcastLoaded(podcast);
            }

            @Override
            public void onDataNotAvailable() {
                // data not available in local storage
                callback.onDataNotAvailable();
            }
        });

    }

    @Override
    public void savePodcast(@NonNull Podcast podcast) {
        podcastsLocalDataSource.savePodcast(podcast);

        // add podcast to cache
        if(cachedPodcasts == null) {
            cachedPodcasts = new LinkedHashMap<>();
        }

        cachedPodcasts.put(podcast.getPodId(), podcast);
    }

    @Override
    public void deleteAllPodcasts() {
        podcastsLocalDataSource.deleteAllPodcasts();

        // clear cache
        if(cachedPodcasts == null) {
            cachedPodcasts = new LinkedHashMap<>();
        }

        cachedPodcasts.clear();
    }

    @Override
    public void deletePodcast(@NonNull String podcastId) {
        podcastsLocalDataSource.deletePodcast(podcastId);

        // remove podcast from cache
        cachedPodcasts.remove(podcastId);
    }

    private void refreshCache(List<Podcast> podcasts) {
        // create new cache if it is null
        if(cachedPodcasts == null) {
            cachedPodcasts = new LinkedHashMap<>();
        }

        cachedPodcasts.clear();

        // add podcasts to cache
        for (Podcast podcast : podcasts) {
            cachedPodcasts.put(podcast.getPodId(), podcast);
        }
    }

    private void refreshLocalDataSource(List<Podcast> podcasts) {
        // clear all podcasts from local database
        podcastsLocalDataSource.deleteAllPodcasts();

        // save all podcasts to local database
        for (Podcast podcast : podcasts) {
            podcastsLocalDataSource.savePodcast(podcast);
        }
    }

    private Podcast getPodcastWithId(@NonNull String podcastId) {
        if(cachedPodcasts == null || cachedPodcasts.isEmpty()) {
            return null;
        } else {
            return cachedPodcasts.get(podcastId);
        }
    }
}
