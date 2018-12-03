package com.tjrushby.podlite.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.tjrushby.podlite.api.ApiResponse;
import com.tjrushby.podlite.util.AppExecutors;

public abstract class NetworkBoundResource<ResultType, RequestType> {

    private final AppExecutors executors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    NetworkBoundResource(AppExecutors executors) {
        this.executors = executors;
        result.setValue(Resource.loading(null));

        LiveData<ResultType> localSource = loadFromDatabase();

        result.addSource(localSource, data -> {
            result.removeSource(localSource);

            if(shouldFetch(data)) {
                fetchFromNetwork(localSource);
            } else {
                result.addSource(localSource,
                        newData -> result.setValue(Resource.success(newData)));
            }
        });
    }

    private void fetchFromNetwork(final LiveData<ResultType> localSource) {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();

        result.addSource(localSource, newData -> result.setValue(Resource.loading(newData)));
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            result.removeSource(localSource);

            if(response.isSuccessful()) {
                executors.diskIO().execute(() -> {
                    saveCallResult(processResponse(response));
                    executors.mainThread().execute(() -> {
                        // request a new LiveData to avoid getting last cached value which may not
                        // have been updated with network results yet
                        result.addSource(loadFromDatabase(),
                                newData -> result.setValue(Resource.success(newData))
                        );
                    });
                });
            } else {
                onFetchFailed();
                result.addSource(localSource,
                        newData -> result.setValue(Resource.error(response.errorMessage, newData)));
            }
        });
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    protected void onFetchFailed() {
        // handle failed network request
    }

    @WorkerThread
    protected RequestType processResponse(ApiResponse<RequestType> response) {
        return response.body;
    }

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDatabase();

    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    @NonNull
    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);
}
