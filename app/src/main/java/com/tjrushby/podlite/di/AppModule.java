package com.tjrushby.podlite.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.google.gson.GsonBuilder;
import com.tjrushby.podlite.api.ITunesService;
import com.tjrushby.podlite.data.PodLiteDatabase;
import com.tjrushby.podlite.data.PodcastsDao;
import com.tjrushby.podlite.util.AppExecutors;
import com.tjrushby.podlite.util.LiveDataCallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
public class AppModule {

    @Provides
    @Singleton
    ITunesService provideITunesService() {
        return new Retrofit.Builder()
                .baseUrl("https://itunes.apple.com/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(ITunesService.class);
    }

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

}
