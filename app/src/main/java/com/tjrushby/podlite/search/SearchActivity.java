package com.tjrushby.podlite.search;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.tjrushby.podlite.R;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;


public class SearchActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    protected DispatchingAndroidInjector<Fragment> fragmentAndroidInjector;

    @Inject
    protected SearchFragment searchFragment;

    @Inject
    protected ViewModelProvider.Factory factory;

    private SearchViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_activity);
        setSupportActionBar(findViewById(R.id.toolbar));

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);

        setupViewFragment();

        viewModel = ViewModelProviders.of(this, factory).get(SearchViewModel.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentFrame, searchFragment)
                .commit();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentAndroidInjector;
    }
}
