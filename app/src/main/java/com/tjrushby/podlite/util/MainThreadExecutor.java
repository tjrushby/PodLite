package com.tjrushby.podlite.util;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/*
 * runs a task on the main thread
 */
public class MainThreadExecutor implements Executor {
    private final Handler handler;

    public MainThreadExecutor() {
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void execute(@NonNull Runnable runnable) {
        handler.post(runnable);
    }
}

