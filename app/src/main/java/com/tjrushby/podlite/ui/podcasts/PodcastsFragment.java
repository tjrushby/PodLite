package com.tjrushby.podlite.ui.podcasts;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tjrushby.podlite.R;
import com.tjrushby.podlite.binding.FragmentDataBindingComponent;
import com.tjrushby.podlite.databinding.PodcastsFragmentBinding;
import com.tjrushby.podlite.ui.common.NavigationController;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

public class PodcastsFragment extends Fragment {

    @Inject
    protected ViewModelProvider.Factory factory;

    @Inject
    NavigationController navigationController;

    android.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    private PodcastsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        PodcastsFragmentBinding dataBinding = DataBindingUtil
                .inflate(inflater, R.layout.podcasts_fragment, container, false,
                        dataBindingComponent);

        viewModel = ViewModelProviders.of(getActivity(), factory).get(PodcastsViewModel.class);

        // subscribe to search event
        viewModel.getSearchEvent().observe(this, (@Nullable Void v) -> searchTask());

        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupFab();
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    public void searchTask() {
        navigationController.navigateToSearch();
    }

    private void setupFab() {
        FloatingActionButton fab = getActivity().findViewById(R.id.fab_add_podcast);
        fab.setOnClickListener((view) -> {
            viewModel.searchPodcasts();
        });
    }
}
