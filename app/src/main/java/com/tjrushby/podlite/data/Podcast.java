package com.tjrushby.podlite.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/*
 * model class for a podcast
 */
@Entity(tableName = "podcasts")
public class Podcast {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "podId")
    private final String mPodId;

    @Nullable
    @ColumnInfo(name = "title")
    private String mTitle;

    @Nullable
    @ColumnInfo(name = "author")
    private String mAuthor;

    @Nullable
    @ColumnInfo(name = "description")
    private String mDescription;

    public Podcast(@NonNull String podId, @Nullable String title, @Nullable String author,
                   @Nullable String description) {
        mPodId = podId;
        mTitle = title;
        mAuthor = author;
        mDescription = description;
    }

    @NonNull
    public String getPodId() {
        return mPodId;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }

    @Nullable
    public String getAuthor() {
        return mAuthor;
    }

    @Nullable
    public String getDescription() {
        return mDescription;
    }
}
