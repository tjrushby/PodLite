package com.tjrushby.podlite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.tjrushby.podlite.ui.common.NavigationController;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;


public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    
    @Inject
    DispatchingAndroidInjector<Fragment> injector;

    @Inject
    NavigationController navigationController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        // set default ui fragment
        if(savedInstanceState == null) {
            navigationController.navigateToPodcasts();
        }
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return injector;
    }
}
