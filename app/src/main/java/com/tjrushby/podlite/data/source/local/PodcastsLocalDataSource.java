package com.tjrushby.podlite.data.source.local;

import android.support.annotation.NonNull;

import com.tjrushby.podlite.data.Podcast;
import com.tjrushby.podlite.data.source.PodcastsDataSource;
import com.tjrushby.podlite.util.AppExecutors;

import java.util.List;

/*
 * local data source class, i.e., Room Database
 */
public class PodcastsLocalDataSource implements PodcastsDataSource {
    private static volatile PodcastsLocalDataSource INSTANCE;

    private PodcastsDao podcastsDao;

    private AppExecutors appExecutors;

    // prevent direct instantiation
    private PodcastsLocalDataSource(@NonNull AppExecutors executors,
                                    @NonNull PodcastsDao podcastsDao) {
        appExecutors = executors;
        this.podcastsDao = podcastsDao;
    }

    public static PodcastsLocalDataSource getInstance(@NonNull AppExecutors executors,
                                                      @NonNull PodcastsDao podcastsDao) {
        if(INSTANCE == null) {
            synchronized (PodcastsLocalDataSource.class) {
                if(INSTANCE == null) {
                    INSTANCE = new PodcastsLocalDataSource(executors, podcastsDao);
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public void getPodcasts(@NonNull final LoadPodcastsCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Podcast> podcasts = podcastsDao.getPodcasts();

                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if(podcasts.isEmpty()) {
                            // no data available
                            callback.onDataNotAvailable();
                        } else {
                            callback.onPodcastsLoaded(podcasts);
                        }
                    }
                });
            }
        };

        appExecutors.diskIO().execute(runnable);

    }

    @Override
    public void getPodcast(@NonNull final String podcastId, @NonNull final GetPodcastCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Podcast podcast = podcastsDao.getPodcastById(podcastId);

                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if(podcast != null) {
                            // found podcast with matching podcastId
                            callback.onPodcastLoaded(podcast);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };

        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void savePodcast(@NonNull final Podcast podcast) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                podcastsDao.insertPodcast(podcast);
            }
        };

        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteAllPodcasts() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                podcastsDao.deleteAllPodcasts();
            }
        };

        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deletePodcast(@NonNull final String podcastId) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                podcastsDao.deletePodcast(podcastId);
            }
        };

        appExecutors.diskIO().execute(runnable);
    }

    static void clearInstance() {
        INSTANCE = null;
    }
}
