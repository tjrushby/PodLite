package com.tjrushby.podlite.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;


/*
 * Room database for the app
 */
@Database(entities = {Podcast.class}, version = 1)
public abstract class PodLiteDatabase extends RoomDatabase {

    public abstract PodcastsDao podcastsDao();

}
