package com.tjrushby.podlite.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.tjrushby.podlite.api.ITunesService;
import com.tjrushby.podlite.api.PodcastsSearchResponse;
import com.tjrushby.podlite.data.Podcast;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class SearchViewModel extends ViewModel {

    private ITunesService iTunesService;

    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    private final MutableLiveData<List<Podcast>> results = new MutableLiveData<>();

    private final MutableLiveData<String> resultsText = new MutableLiveData<>();

    @Inject
    public SearchViewModel(ITunesService iTunesService) {
        this.iTunesService = iTunesService;
    }

    void searchByTerm(String searchTerm) {
        Timber.d("searchByTerm: %s", searchTerm);

        results.setValue(null);
        loading.setValue(true);

        Call<PodcastsSearchResponse> call =
                iTunesService.getPodcastsByTerm(searchTerm);

        call.enqueue(new Callback<PodcastsSearchResponse>() {
            @Override
            public void onResponse(Call<PodcastsSearchResponse> call,
                                   Response<PodcastsSearchResponse> response) {

                if(response.body() != null) {
                    results.setValue(response.body().getResults());

                    if(response.body().getResults().size() > 0) {
                        resultsText.setValue(null);
                    } else {
                        resultsText.setValue("No results for '" + searchTerm + "'");
                    }
                } else {
                    Timber.d("Error Code: %s", response.code());
                    Timber.d("Error Message: %s", response.message());
                    results.setValue(null);
                    resultsText.setValue("Too many requests. Please wait a moment and try again.");
                }

                loading.setValue(false);
            }

            @Override
            public void onFailure(Call<PodcastsSearchResponse> call, Throwable t) {
                Timber.d("Error: %s", t.getCause());

                loading.setValue(false);
                results.setValue(null);
                resultsText.setValue("An error occurred.");
            }
        });
    }

    LiveData<Boolean> isLoading() {
        return loading;
    }

    LiveData<List<Podcast>> getResults() {
        return results;
    }

    public MutableLiveData<String> getResultsText() {
        return resultsText;
    }
}
