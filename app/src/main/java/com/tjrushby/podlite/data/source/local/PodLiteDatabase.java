package com.tjrushby.podlite.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.tjrushby.podlite.data.Podcast;


/*
 * Room database for the app
 */
@Database(entities = {Podcast.class}, version = 1)
public abstract class PodLiteDatabase extends RoomDatabase {
    private static PodLiteDatabase INSTANCE;

    public abstract PodcastsDao podcastsDao();

    private static final Object sLock = new Object();

    public static PodLiteDatabase getInstance(Context context) {
        synchronized (sLock) {
            if(INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        PodLiteDatabase.class, "PodLite.db"
                ).build();
            }

            return INSTANCE;
        }
    }

}
