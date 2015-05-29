package com.applaudo.nflexperience.android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.applaudo.nflexperience.android.dummy.DummyContent;
import com.applaudo.nflexperience.android.model.Venue;
import com.applaudo.nflexperience.android.rest.PhunwareNflApi;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class VenueListFragment extends Fragment {

    private final static String API_URL = "https://s3.amazonaws.com/jon-hancock-phunware";

    private RecyclerView mRecyclerView;
    private VenueRecycleAdapter mVenueRecycleAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types of parameters
    public static VenueListFragment newInstance(String param1, String param2) {
        VenueListFragment fragment = new VenueListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static VenueListFragment newInstance() {
        VenueListFragment fragment = new VenueListFragment();
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public VenueListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(API_URL)
                .build();

        PhunwareNflApi methods = restAdapter.create(PhunwareNflApi.class);
        Callback<List<Venue>> callback = new Callback<List<Venue>>() {
            @Override
            public void success(List<Venue> o, Response response) {
                mVenueRecycleAdapter.venueDataSet.addAll(o);
                mVenueRecycleAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                //Log.v("myApp", retrofitError.getMessage());
            }
        };
        methods.getVenues(callback);

        // TODO: Change Adapter to display your content
       /* setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS));*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_venue_list, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.venue_list_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mVenueRecycleAdapter);
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
      //  mListener = null;
        mVenueRecycleAdapter.setOnVenueSelectedListener(null);
    }

   // @Override
   // public void onListItemClick(ListView l, View v, int position, long id) {
   //     super.onListItemClick(l, v, position, id);

   //    if (null != mListener) {
   //         mListener.onVenueSelected(/*DummyContent.ITEMS.get(position).id*/position);
    //    }
   // }

}
