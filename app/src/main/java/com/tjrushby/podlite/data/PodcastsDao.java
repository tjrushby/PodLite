package com.tjrushby.podlite.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/*
 * data access object for podcasts table
 */
@Dao
public interface PodcastsDao {
    /*
     * select all podcasts from the podcasts table
     */
    @Query("SELECT * FROM Podcasts")
    LiveData<List<Podcast>> getPodcasts();

    /*
     * select a podcast by id
     */
    @Query("SELECT * FROM Podcasts WHERE collection_id = :collectionId")
    LiveData<Podcast> getPodcastById(String collectionId);

    /*
     * insert podcast to table, replace any existing podcast with the same id
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPodcast(Podcast podcast);

    /*
     * deletes podcast from the table
     */
    @Query("DELETE FROM Podcasts WHERE collection_id =:collectionId")
    void deletePodcast(String collectionId);

    /*
     * deletes all podcasts from the table
     */
    @Query("DELETE FROM Podcasts")
    void deleteAllPodcasts();

}
