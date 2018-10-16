package com.tjrushby.podlite.data.source.local;

import android.support.annotation.NonNull;

import com.tjrushby.podlite.data.Podcast;
import com.tjrushby.podlite.data.source.PodcastsDataSource;
import com.tjrushby.podlite.util.AppExecutors;

import java.util.List;

import javax.inject.Inject;

/*
 * local data source class, i.e., Room Database
 */
public class PodcastsLocalDataSource implements PodcastsDataSource {

    private PodcastsDao podcastsDao;

    private AppExecutors appExecutors;

    @Inject
    public PodcastsLocalDataSource(@NonNull AppExecutors appExecutors,
                                   @NonNull PodcastsDao podcastsDao) {
        this.appExecutors = appExecutors;
        this.podcastsDao = podcastsDao;
    }

    @Override
    public void getPodcasts(@NonNull final LoadPodcastsCallback callback) {
        Runnable runnable = () -> {
            final List<Podcast> podcasts = podcastsDao.getPodcasts();

            appExecutors.mainThread().execute(() -> {
                if(podcasts.isEmpty()) {
                    // no data available
                    callback.onDataNotAvailable();
                } else {
                    callback.onPodcastsLoaded(podcasts);
                }
            });
        };

        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getPodcast(@NonNull final String podcastId, @NonNull final GetPodcastCallback callback) {
        Runnable runnable = () -> {
            final Podcast podcast = podcastsDao.getPodcastById(podcastId);

            appExecutors.mainThread().execute(() -> {
                if(podcast != null) {
                    // found podcast with matching podcastId
                    callback.onPodcastLoaded(podcast);
                } else {
                    callback.onDataNotAvailable();
                }
            });
        };

        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void savePodcast(@NonNull final Podcast podcast) {
        Runnable runnable = () -> {
            podcastsDao.insertPodcast(podcast);
        };

        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteAllPodcasts() {
        Runnable runnable = () -> {
            podcastsDao.deleteAllPodcasts();
        };

        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deletePodcast(@NonNull final String podcastId) {
        Runnable runnable = () -> {
            podcastsDao.deletePodcast(podcastId);
        };

        appExecutors.diskIO().execute(runnable);
    }
}
