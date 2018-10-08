package com.tjrushby.podlite.data.source;

/*
 * interface for accessing podcasts data
 */

import android.support.annotation.NonNull;

import com.tjrushby.podlite.data.Podcast;

import java.util.List;

public interface PodcastsDataSource {

    interface LoadPodcastsCallback {
        void onPodcastsLoaded(List<Podcast> podcasts);

        void onDataNotAvailable();
    }

    interface GetPodcastCallback {
        void onPodcastLoaded(Podcast podcast);

        void onDataNotAvailable();
    }

    void getPodcasts(@NonNull LoadPodcastsCallback callback);

    void getPodcast(@NonNull String podcastId, @NonNull GetPodcastCallback callback);

    void savePodcast(@NonNull Podcast podcast);

    void deleteAllPodcasts();

    void deletePodcast(@NonNull String podcastId);
}
