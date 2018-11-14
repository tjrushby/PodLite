package com.tjrushby.podlite.api;

import com.google.gson.annotations.SerializedName;
import com.tjrushby.podlite.data.Podcast;

import java.util.List;

/*
 * POJO to hold podcast search responses
 */
public class PodcastsSearchResponse {

    @SerializedName("results")
    private List<Podcast> results;

    public List<Podcast> getResults() {
        return results;
    }

    public void setResults(List<Podcast> results) {
        this.results = results;
    }
}
