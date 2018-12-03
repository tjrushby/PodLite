package com.tjrushby.podlite.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

import com.tjrushby.podlite.api.ApiResponse;
import com.tjrushby.podlite.util.AppExecutors;

import timber.log.Timber;

public abstract class NetworkOnlyResource<ResultType, RequestType> {

    private AppExecutors executors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkOnlyResource(AppExecutors executors) {
        this.executors = executors;
        result.setValue(Resource.loading(null));

        fetchFromNetwork();
    }

    private void fetchFromNetwork() {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();

        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);

            if(response != null && response.isSuccessful()) {
                executors.diskIO().execute(() -> {
                    RequestType request = processResponse(response);
                    ResultType result = processResult(request);

                    executors.mainThread().execute(() -> setResultValue(Resource.success(result)));
                });
            } else {
                // fetch failed
                Timber.e(response.errorMessage);
                onFetchFailed();
            }
        });
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    protected void onFetchFailed() {
        // handle failed request
    }

    @MainThread
    private void setResultValue(Resource<ResultType> newResult) {
        if(result.getValue() != newResult) {
            result.setValue(newResult);
        }
    }

    @WorkerThread
    protected RequestType processResponse(ApiResponse<RequestType> response) {
        return response.body;
    }

    @WorkerThread
    protected abstract ResultType processResult(RequestType request);

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();
}
