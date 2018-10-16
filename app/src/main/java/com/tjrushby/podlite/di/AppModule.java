package com.tjrushby.podlite.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.tjrushby.podlite.data.source.PodcastsRepository;
import com.tjrushby.podlite.data.source.local.PodLiteDatabase;
import com.tjrushby.podlite.data.source.local.PodcastsDao;
import com.tjrushby.podlite.data.source.local.PodcastsLocalDataSource;
import com.tjrushby.podlite.util.AppExecutors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = ViewModelModule.class)
public class AppModule {

    @Provides
    @Singleton
    PodLiteDatabase providePodLiteDatabase(Application app) {
        return Room.databaseBuilder(app, PodLiteDatabase.class, "podlite.db").build();
    }

    @Provides
    @Singleton
    PodcastsDao providePodcastsDao(PodLiteDatabase db) {
        return db.podcastsDao();
    }

    @Provides
    @Singleton
    AppExecutors provideAppExecutors() {
        return new AppExecutors();
    }

    @Provides
    @Singleton
    PodcastsLocalDataSource providePodcastsLocalDataSource(AppExecutors appExecutors,
                                                           PodcastsDao podcastsDao) {
        return new PodcastsLocalDataSource(appExecutors, podcastsDao);
    }

    @Provides
    @Singleton
    PodcastsRepository providePodcastsRepository(PodcastsLocalDataSource localDataSource) {
        return new PodcastsRepository(localDataSource);
    }
}
