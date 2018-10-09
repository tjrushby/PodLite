package com.tjrushby.podlite.data.source;

import android.support.annotation.NonNull;

import com.tjrushby.podlite.data.Podcast;

public class PodcastsRemoteDataSource implements PodcastsDataSource {

    private static volatile PodcastsRemoteDataSource INSTANCE;

    // prevent direct instantiation
    private PodcastsRemoteDataSource() {

    }

    public static PodcastsRemoteDataSource getInstance() {
        if(INSTANCE == null) {
            synchronized (PodcastsRemoteDataSource.class) {
                if(INSTANCE == null) {
                    INSTANCE = new PodcastsRemoteDataSource();
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public void getPodcasts(@NonNull LoadPodcastsCallback callback) {

    }

    @Override
    public void getPodcast(@NonNull String podcastId, @NonNull GetPodcastCallback callback) {

    }

    @Override
    public void savePodcast(@NonNull Podcast podcast) {

    }

    @Override
    public void deleteAllPodcasts() {

    }

    @Override
    public void deletePodcast(@NonNull String podcastId) {

    }
}
