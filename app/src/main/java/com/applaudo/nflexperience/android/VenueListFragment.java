package com.applaudo.nflexperience.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applaudo.nflexperience.android.model.Venue;
import com.applaudo.nflexperience.android.rest.ApiClient;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class VenueListFragment extends Fragment {

    private VenueRecycleAdapter mVenueRecycleAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public VenueListFragment() {
    }

    public static VenueListFragment newInstance() {
        return new VenueListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Callback<List<Venue>> callback = new Callback<List<Venue>>() {
            @Override
            public void success(List<Venue> o, Response response) {
                mVenueRecycleAdapter.venueDataSet.addAll(o);
                mVenueRecycleAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.v("myApp", retrofitError.getMessage());
            }
        };

        ApiClient.getInstance().getVenues(callback);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_venue_list, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.venue_list_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mVenueRecycleAdapter);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mVenueRecycleAdapter = new VenueRecycleAdapter();
            mVenueRecycleAdapter.setOnVenueSelectedListener((VenueRecycleAdapter.OnVenueSelectedListener) activity);
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnVenueSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mVenueRecycleAdapter.setOnVenueSelectedListener(null);
    }

}
