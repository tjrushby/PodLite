package com.tjrushby.podlite.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/*
 * model class for a podcast
 */
@Entity(tableName = "podcasts", primaryKeys = "collection_id")
public class Podcast {

    @NonNull
    @ColumnInfo(name = "collection_id")
    @SerializedName("collectionId")
    private final String collectionId;

    @ColumnInfo(name = "im:name")
    @SerializedName("collectionName")
    private String collectionName;

    @ColumnInfo(name = "artist_name")
    @SerializedName("artistName")
    private String artistName;

    @ColumnInfo(name = "feed_url")
    @SerializedName("feedUrl")
    private String feedUrl;

    @ColumnInfo(name = "artwork_url_100")
    @SerializedName("artworkUrl100")
    private String artworkUrl100;

    @ColumnInfo(name = "track_count")
    @SerializedName("trackCount")
    private int trackCount;

    @ColumnInfo(name = "primary_genre")
    @SerializedName("primaryGenreName")
    private String primaryGenre;

    @ColumnInfo(name = "content_advisory_rating")
    @SerializedName("contentAdvisoryRating")
    private String contentAdvisoryRating;

    public Podcast(@NonNull String collectionId, String artistName, String feedUrl,
                   String collectionName, String artworkUrl100, int trackCount, String primaryGenre,
                   String contentAdvisoryRating) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.artistName = artistName;
        this.feedUrl = feedUrl;
        this.artworkUrl100 = artworkUrl100;
        this.trackCount = trackCount;
        this.primaryGenre = primaryGenre;
        this.contentAdvisoryRating = contentAdvisoryRating;
    }

    @NonNull
    public String getCollectionId() {
        return collectionId;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public int getTrackCount() {
        return trackCount;
    }

    public String getPrimaryGenre() {
        return primaryGenre;
    }

    public String getContentAdvisoryRating() {
        return contentAdvisoryRating;
    }
}
