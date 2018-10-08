package com.tjrushby.podlite.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.tjrushby.podlite.data.Podcast;

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
    List<Podcast> getPodcasts();

    /*
     * select a podcast by id
     */
    @Query("SELECT * FROM Podcasts WHERE podId = :podId")
    Podcast getPodcastById(String podId);

    /*
     * insert podcast to table, replace any existing podcast with the same id
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPodcast(Podcast podcast);

    /*
     * deletes podcast from the table
     */
    @Query("DELETE FROM Podcasts WHERE podId =:podcastId")
    void deletePodcast(String podcastId);

    /*
     * deletes all podcasts from the table
     */
    @Query("DELETE FROM Podcasts")
    void deleteAllPodcasts();

}
