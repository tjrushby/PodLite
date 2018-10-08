package com.tjrushby.podlite.data.source;

import android.support.annotation.NonNull;

import com.tjrushby.podlite.data.Podcast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PodcastsRepository implements PodcastsDataSource {
    private volatile static PodcastsRepository INSTANCE = null;

    private final PodcastsDataSource podcastsLocalDataSource;

    private final PodcastsDataSource podcastsRemoteDataSource;

    /*
     * this has package visibility for testing
     */
    protected Map<String, Podcast> mCachedPodcasts;

    /*
     * marks the cache as invalid
     */
    protected boolean mCacheIsDirty;

    // prevent direct instantiation
    private PodcastsRepository(@NonNull PodcastsDataSource podcastsLocalDataSource,
                               @NonNull PodcastsDataSource podcastsRemoteDataSource) {
        this.podcastsLocalDataSource = podcastsLocalDataSource;
        this.podcastsRemoteDataSource = podcastsRemoteDataSource;
    }

    /*
     * returns the single instance of this class, creating it if necessary
     */
    public static PodcastsRepository getInstance(PodcastsDataSource podcastsLocalDataSource,
                                                 PodcastsDataSource podcastsRemoteDataSource) {
        if(INSTANCE == null) {
            synchronized (PodcastsRepository.class) {
                if(INSTANCE == null) {
                    INSTANCE = new PodcastsRepository(
                            podcastsLocalDataSource,
                            podcastsRemoteDataSource
                    );
                }
            }
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    /*
     * gets podcasts from the cache, local data source, or remote data source depending on which is
     * available. If no sources retrieve data LoadPodcastsCallback#onDataNotAvailable is called
     */
    @Override
    public void getPodcasts(@NonNull final LoadPodcastsCallback callback) {
        // return podcasts from cache if available
        if(mCachedPodcasts != null && !mCacheIsDirty) {
            callback.onPodcastsLoaded(new ArrayList<>(mCachedPodcasts.values()));
            return;
        }

        // if the cache is dirty we need to retrieve data from remote source
        if(mCacheIsDirty) {
            getPodcastsFromRemoteDataSource(callback);
        } else {
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
                    // data not available in local storage, attempt to retrieve from remote source
                    getPodcastsFromRemoteDataSource(callback);
                }
            });
        }
    }

    /*
     * gets podcast from most readily available data source.
     * If no sources contain podcastId GetPodcastCallback#onDataNotAvailable is called
     */
    @Override
    public void getPodcast(@NonNull final String podcastId, @NonNull final GetPodcastCallback callback) {
        // return podcast from cache if available
        Podcast podcast = getPodcastWithId(podcastId);

        if(podcast != null) {
            callback.onPodcastLoaded(podcast);
            return;
        }

        // check local source for podcast, otherwise attempt to retrieve from remote source
        podcastsLocalDataSource.getPodcast(podcastId, new GetPodcastCallback() {
            @Override
            public void onPodcastLoaded(Podcast podcast) {
                if(podcast == null) {
                    onDataNotAvailable();
                    return;
                }

                // add podcast to cache
                if(mCachedPodcasts == null) {
                    mCachedPodcasts = new LinkedHashMap<>();
                }

                mCachedPodcasts.put(podcast.getPodId(), podcast);
                callback.onPodcastLoaded(podcast);
            }

            @Override
            public void onDataNotAvailable() {
                // attempt to retrieve podcast from remote source
                podcastsRemoteDataSource.getPodcast(podcastId, new GetPodcastCallback() {
                    @Override
                    public void onPodcastLoaded(Podcast podcast) {
                        if(podcast == null) {
                            onDataNotAvailable();
                            return;
                        }

                        // add podcast to cache
                        if(mCachedPodcasts == null) {
                            mCachedPodcasts = new LinkedHashMap<>();
                        }

                        mCachedPodcasts.put(podcast.getPodId(), podcast);
                        callback.onPodcastLoaded(podcast);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });

    }

    @Override
    public void savePodcast(@NonNull Podcast podcast) {
        podcastsLocalDataSource.savePodcast(podcast);

        // add podcast to cache
        if(mCachedPodcasts == null) {
            mCachedPodcasts = new LinkedHashMap<>();
        }

        mCachedPodcasts.put(podcast.getPodId(), podcast);
    }

    @Override
    public void deleteAllPodcasts() {
        podcastsLocalDataSource.deleteAllPodcasts();

        // clear cache
        if(mCachedPodcasts == null) {
            mCachedPodcasts = new LinkedHashMap<>();
        }

        mCachedPodcasts.clear();
    }

    @Override
    public void deletePodcast(@NonNull String podcastId) {
        podcastsLocalDataSource.deletePodcast(podcastId);

        // remove podcast from cache
        mCachedPodcasts.remove(podcastId);
    }

    private void getPodcastsFromRemoteDataSource(@NonNull final LoadPodcastsCallback callback) {
        podcastsRemoteDataSource.getPodcasts(new LoadPodcastsCallback() {
            @Override
            public void onPodcastsLoaded(List<Podcast> podcasts) {
                refreshCache(podcasts);
                refreshLocalDataSource(podcasts);

                callback.onPodcastsLoaded(podcasts);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshCache(List<Podcast> podcasts) {
        // create new cache if it is null
        if(mCachedPodcasts == null) {
            mCachedPodcasts = new LinkedHashMap<>();
        }

        mCachedPodcasts.clear();

        // add podcasts to cache
        for(Podcast podcast : podcasts) {
            mCachedPodcasts.put(podcast.getPodId(), podcast);
        }

        // mark cache as clean
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<Podcast> podcasts) {
        // clear all podcasts from local database
        podcastsLocalDataSource.deleteAllPodcasts();

        // save all podcasts to local database
        for(Podcast podcast : podcasts) {
            podcastsLocalDataSource.savePodcast(podcast);
        }
    }

    private Podcast getPodcastWithId(@NonNull String podcastId) {
        if(mCachedPodcasts != null || !mCachedPodcasts.isEmpty()) {
            return null;
        } else {
            return mCachedPodcasts.get(podcastId);
        }
    }
}
