package com.tjrushby.podlite;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;

    @Inject
    public ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
        this.creators = creators;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Provider<? extends ViewModel> creator = creators.get(modelClass);

        if(creator == null) {
            for(Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>> entry :
                    creators.entrySet()) {
                if(modelClass.isAssignableFrom(entry.getKey())) {
                    creator = entry.getValue();
                    break;
                }
            }
        }

        // modelClass was not assignable from key, throw exception
        if(creator == null) {
            throw new IllegalArgumentException("Unknown model class: " + modelClass);
        }

        // try and return ViewModel
        try {
            return (T) creator.get();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
