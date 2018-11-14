package com.tjrushby.podlite.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ITunesService {

    @GET("search?media=podcast")
    Call<PodcastsSearchResponse> getPodcastsByTerm(@Query("term") String searchTerm);
}
