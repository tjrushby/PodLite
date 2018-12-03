package com.tjrushby.podlite.ui.podcastdetails;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tjrushby.podlite.R;
import com.tjrushby.podlite.binding.FragmentDataBindingComponent;
import com.tjrushby.podlite.databinding.DetailsFragmentBinding;
import com.tjrushby.podlite.util.AutoClearedValue;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class PodcastDetailsFragment extends Fragment {

    private static final String COLLECTION_ID_KEY = "collection_id";

    @Inject
    ViewModelProvider.Factory factory;

    AutoClearedValue<DetailsFragmentBinding> binding;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    private PodcastDetailsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        DetailsFragmentBinding dataBinding = DataBindingUtil
                .inflate(inflater, R.layout.details_fragment, container, false,
                        dataBindingComponent);

        binding = new AutoClearedValue<>(this, dataBinding);

        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity(), factory).get(PodcastDetailsViewModel.class);

        Bundle args = getArguments();
        if(args != null && args.containsKey(COLLECTION_ID_KEY)) {
            // todo set collectionId in viewModel from args
        }


        initViews();
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    public static PodcastDetailsFragment create(String collectionId) {
        PodcastDetailsFragment fragment = new PodcastDetailsFragment();

        Bundle args = new Bundle();
        args.putString(COLLECTION_ID_KEY, collectionId);
        fragment.setArguments(args);

        return fragment;
    }

    /*
     * observe LiveData for podcast, isLoading, and results
     */
    private void initViews() {
        viewModel.getPodcast().observe(this, podcast -> {
            binding.get().setPodcast(podcast);
        });
    }
}
