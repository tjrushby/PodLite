package com.tjrushby.podlite.podcasts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tjrushby.podlite.R;

public class PodcastsFragment extends Fragment {
    private PodcastsViewModel podcastsViewModel;

    public PodcastsFragment() {

    }

    public static PodcastsFragment newInstance() {
        return new PodcastsFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        podcastsViewModel = PodcastsActivity.obtainViewModel(getActivity());

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupFab();
    }

    private void setupFab() {
        FloatingActionButton fab = getActivity().findViewById(R.id.fab_add_podcast);
        fab.setOnClickListener((view) -> podcastsViewModel.addNewPodcast());
    }
}
