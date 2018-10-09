package com.tjrushby.podlite;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.tjrushby.podlite.data.source.PodcastsRemoteDataSource;
import com.tjrushby.podlite.data.source.PodcastsRepository;
import com.tjrushby.podlite.data.source.local.PodLiteDatabase;
import com.tjrushby.podlite.data.source.local.PodcastsLocalDataSource;
import com.tjrushby.podlite.podcasts.PodcastsViewModel;
import com.tjrushby.podlite.util.AppExecutors;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static volatile ViewModelFactory INSTANCE;

    private final Application application;

    private final PodcastsRepository podcastsRepo;

    public static ViewModelFactory getInstance(Application application) {
        if(INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if(INSTANCE == null) {
                    PodLiteDatabase db =
                            PodLiteDatabase.getInstance(application.getApplicationContext());

                    INSTANCE = new ViewModelFactory(application,
                            PodcastsRepository.getInstance(
                                    PodcastsLocalDataSource.getInstance(
                                            new AppExecutors(), db.podcastsDao()
                                    ), PodcastsRemoteDataSource.getInstance()
                            )
                    );
                }
            }
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private ViewModelFactory(Application application, PodcastsRepository podcastsRepo) {
        this.application = application;
        this.podcastsRepo = podcastsRepo;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(PodcastsViewModel.class)) {
            return (T) new PodcastsViewModel(application, podcastsRepo);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
