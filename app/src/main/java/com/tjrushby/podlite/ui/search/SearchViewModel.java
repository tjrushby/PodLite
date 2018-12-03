package com.tjrushby.podlite.ui.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.tjrushby.podlite.data.Podcast;
import com.tjrushby.podlite.data.PodcastsRepository;
import com.tjrushby.podlite.data.Resource;
import com.tjrushby.podlite.data.Status;
import com.tjrushby.podlite.util.AbsentLiveData;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

public class SearchViewModel extends ViewModel {

    private final LiveData<Boolean> isLoading;

    private final LiveData<Resource<List<Podcast>>> results;

    private final LiveData<String> resultsText;

    private final MutableLiveData<String> searchTerm = new MutableLiveData<>();

    @Inject
    SearchViewModel(PodcastsRepository podRepo) {
        results = Transformations.switchMap(searchTerm, input -> {
            if(input == null || input.trim().length() == 0) {
                return AbsentLiveData.create();
            } else {
                return podRepo.searchPodcasts(input);
            }
        });

        isLoading = Transformations.switchMap(results, resource -> {
            if(resource != null) {
                switch (resource.status) {
                    case LOADING:
                        return new LiveData<Boolean>() {
                            @Override
                            protected void onActive() {
                                super.onActive();
                                setValue(true);
                            }
                        };
                }
            }

            return new LiveData<Boolean>() {
                @Override
                protected void onActive() {
                    super.onActive();
                    setValue(false);
                }
            };
        });

        resultsText = Transformations.switchMap(results, resource -> {
            if(resource != null) {
                if(resource.status == Status.SUCCESS && resource.data.isEmpty()) {
                    MutableLiveData<String> newResultsText = new MutableLiveData<>();
                    newResultsText.setValue("No results found for " + searchTerm.getValue());
                    return newResultsText;
                }
            }

            return AbsentLiveData.create();
        });
    }

    void setSearchTerm(@NonNull String originalInput) {
        String input = originalInput.toLowerCase(Locale.getDefault()).trim();

        if(Objects.equals(input, searchTerm.getValue())) {
            return;
        }

        searchTerm.setValue(input);
    }



    LiveData<Boolean> isLoading() {
        return isLoading;
    }

    LiveData<Resource<List<Podcast>>> getResults() {
        return results;
    }

    LiveData<String> getResultsText() {
        return resultsText;
    }
}
