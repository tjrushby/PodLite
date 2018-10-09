package com.tjrushby.podlite.podcasts;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.tjrushby.podlite.R;
import com.tjrushby.podlite.ViewModelFactory;


public class PodcastsActivity extends AppCompatActivity implements PodcastsNavigator {

    private PodcastsViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcasts);

        setupViewFragment();

        obtainViewModel(this);
    }

    public static PodcastsViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        return ViewModelProviders.of(activity, factory).get(PodcastsViewModel.class);
    }

    private void setupViewFragment() {
        PodcastsFragment podcastsFragment =
                (PodcastsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if(podcastsFragment != null) {
            // create the fragment
            podcastsFragment = PodcastsFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contentFrame, podcastsFragment)
                    .commit();
        }
    }

    @Override
    public void addNewPodcast() {
        // start search activity for result
    }
}
