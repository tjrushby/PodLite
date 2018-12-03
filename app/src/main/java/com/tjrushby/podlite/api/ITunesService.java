package com.tjrushby.podlite.api;

import android.arch.lifecycle.LiveData;

import com.tjrushby.podlite.data.Podcast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ITunesService {

    @GET("search?media=podcast")
    LiveData<ApiResponse<PodcastsSearchResponse>> getPodcastsByTerm(@Query("term") String searchTerm);

    @GET("lookup")
    LiveData<ApiResponse<Podcast>> getPodcastByCollectionId(@Query("id") String collectionId);
}
