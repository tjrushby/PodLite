package com.tjrushby.podlite.ui.common;

import android.support.v4.app.FragmentManager;

import com.tjrushby.podlite.MainActivity;
import com.tjrushby.podlite.R;
import com.tjrushby.podlite.ui.podcasts.PodcastsFragment;
import com.tjrushby.podlite.ui.search.SearchFragment;

import javax.inject.Inject;

import timber.log.Timber;

public class NavigationController {
    private final int containerId;
    private final FragmentManager fragmentManger;

    @Inject
    public NavigationController(MainActivity mainActivity) {
        this.containerId = R.id.contentFrame;
        this.fragmentManger = mainActivity.getSupportFragmentManager();
    }

    public void navigateToPodcasts() {
        PodcastsFragment podcastsFragment = new PodcastsFragment();

        fragmentManger.beginTransaction()
                .replace(containerId, podcastsFragment)
                .commit();
    }

    public void navigateToSearch() {
        SearchFragment searchFragment = new SearchFragment();

        fragmentManger.beginTransaction()
                .replace(containerId, searchFragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}
