package com.tjrushby.podlite.podcasts;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.tjrushby.podlite.R;
import com.tjrushby.podlite.data.source.PodcastsRepository;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class PodcastsActivity extends AppCompatActivity
        implements HasSupportFragmentInjector, PodcastsNavigator {

    @Inject
    protected DispatchingAndroidInjector<Fragment> fragmentAndroidInjector;

    @Inject
    protected ViewModelProvider.Factory factory;

    @Inject
    protected PodcastsFragment podcastsFragment;

    @Inject
    protected PodcastsRepository repo;

    private PodcastsViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_podcasts);

        setupViewFragment();

        viewModel = ViewModelProviders.of(this, factory).get(PodcastsViewModel.class);

    }

    private void setupViewFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentFrame, podcastsFragment)
                .commit();

    }

    @Override
    public void addNewPodcast() {
        // start search activity for result
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentAndroidInjector;
    }
}
