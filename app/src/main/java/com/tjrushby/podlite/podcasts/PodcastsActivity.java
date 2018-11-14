package com.tjrushby.podlite.podcasts;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.tjrushby.podlite.R;
import com.tjrushby.podlite.data.source.PodcastsRepository;
import com.tjrushby.podlite.search.SearchActivity;

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
    protected Intent intent;

    @Inject
    protected PodcastsFragment podcastsFragment;

    @Inject
    protected PodcastsRepository repo;

    @Inject
    protected ViewModelProvider.Factory factory;

    private PodcastsViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.podcasts_activity);

        setupViewFragment();

        viewModel = ViewModelProviders.of(this, factory).get(PodcastsViewModel.class);

        // subscribe to search event
        viewModel.getSearchEvent().observe(this, (@Nullable Void v) -> searchTask());

    }

    private void setupViewFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentFrame, podcastsFragment)
                .commit();
    }

    @Override
    public void searchTask() {
        intent.setClass(this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentAndroidInjector;
    }
}
