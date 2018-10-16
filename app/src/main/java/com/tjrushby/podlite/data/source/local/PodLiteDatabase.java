package com.tjrushby.podlite.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.tjrushby.podlite.data.Podcast;


/*
 * Room database for the app
 */
@Database(entities = {Podcast.class}, version = 1)
public abstract class PodLiteDatabase extends RoomDatabase {

    public abstract PodcastsDao podcastsDao();

}
