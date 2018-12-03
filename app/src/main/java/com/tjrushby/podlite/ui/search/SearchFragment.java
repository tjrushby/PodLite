package com.tjrushby.podlite.ui.search;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.tjrushby.podlite.R;
import com.tjrushby.podlite.binding.FragmentDataBindingComponent;
import com.tjrushby.podlite.ui.common.NavigationController;
import com.tjrushby.podlite.ui.common.PodcastAdapter;
import com.tjrushby.podlite.databinding.SearchFragmentBinding;
import com.tjrushby.podlite.util.AutoClearedValue;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

public class SearchFragment extends Fragment {

    @Inject
    NavigationController navigationController;

    @Inject
    ViewModelProvider.Factory factory;

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

        ((AppCompatActivity) getActivity()).setSupportActionBar(getActivity().findViewById(R.id.toolbar));

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);

        initViews();

        PodcastAdapter rvAdapter = new PodcastAdapter(dataBindingComponent,
                podcast -> navigationController.navigateToPodcastDetails(podcast.getCollectionId()));
        binding.get().rvSearchResults.setAdapter(rvAdapter);
        adapter = new AutoClearedValue<>(this, rvAdapter);
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryText) {
                if(queryText != null) {
                    viewModel.setSearchTerm(queryText);

                    // focus on RecyclerView
                    searchView.clearFocus();
                    binding.get().rvSearchResults.requestFocus();
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String queryText) {
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                // hide soft keyboard if visible
                InputMethodManager inMethodManager = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

                if(inMethodManager.isActive()) {
                    inMethodManager.hideSoftInputFromWindow(
                            getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS
                    );
                }

                getActivity().onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * observe LiveData for isLoading, results, and resultsText
     */
    private void initViews() {
        viewModel.isLoading().observe(getViewLifecycleOwner(), loading -> {
            binding.get().setLoading(loading);
            binding.get().executePendingBindings();
        });

        viewModel.getResults().observe(getViewLifecycleOwner(), results -> {
            if(results != null) {
                adapter.get().setItems(results.data);
                binding.get().executePendingBindings();
            }
        });

        viewModel.getResultsText().observe(getViewLifecycleOwner(), resultsText -> {
            binding.get().setResultsText(resultsText);
            binding.get().executePendingBindings();
        });
    }
}
