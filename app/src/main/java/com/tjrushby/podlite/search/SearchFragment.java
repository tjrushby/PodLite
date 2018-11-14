package com.tjrushby.podlite.search;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tjrushby.podlite.R;
import com.tjrushby.podlite.binding.FragmentDataBindingComponent;
import com.tjrushby.podlite.common.PodcastAdapter;
import com.tjrushby.podlite.databinding.SearchFragmentBinding;
import com.tjrushby.podlite.util.AutoClearedValue;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class SearchFragment extends Fragment {

    @Inject
    protected ViewModelProvider.Factory factory;

    AutoClearedValue<SearchFragmentBinding> binding;

    AutoClearedValue<PodcastAdapter> adapter;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    private SearchViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        SearchFragmentBinding dataBinding = DataBindingUtil
                .inflate(inflater, R.layout.search_fragment, container, false,
                        dataBindingComponent);

        binding = new AutoClearedValue<>(this, dataBinding);

        setHasOptionsMenu(true);

        return dataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity(), factory).get(SearchViewModel.class);

        initViews();

        PodcastAdapter rvAdapter = new PodcastAdapter(dataBindingComponent);
        binding.get().rvSearchResults.setAdapter(rvAdapter);
        adapter = new AutoClearedValue<>(this, rvAdapter);
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryText) {
                if(queryText != null) {
                    viewModel.searchByTerm(queryText);
                    searchView.clearFocus();
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String queryText) {
                return true;
            }
        });
    }

    /*
     * observe LiveData for isLoading, results, and resultsText
     */
    private void initViews() {
        viewModel.isLoading().observe(this, loading -> {
            binding.get().setLoading(loading);
            binding.get().executePendingBindings();
        });

        viewModel.getResults().observe(this, result -> {
            adapter.get().setItems(result);
            binding.get().executePendingBindings();
        });

        viewModel.getResultsText().observe(getViewLifecycleOwner(), resultsText -> {
            binding.get().setResultsText(resultsText);
            binding.get().executePendingBindings();
        });
    }
}
