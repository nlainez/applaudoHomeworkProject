package com.applaudo.nflexperience.android;


import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.applaudo.nflexperience.android.model.Venue;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VenueDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VenueDetailFragment extends Fragment {

    final static String ARG_VENUE = "venue";
    final static String SER_KEY = "com.applaudo.nflexperience.android.model";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Venue mVenue;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VenueDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VenueDetailFragment newInstance(String param1, String param2) {
        VenueDetailFragment fragment = new VenueDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static VenueDetailFragment newInstance() {
        VenueDetailFragment fragment = new VenueDetailFragment();
        return fragment;
    }

    public VenueDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if (args != null) {
            updateVenueView((Venue) args.getSerializable(SER_KEY));
        } else if (mVenue != null) {
            updateVenueView(mVenue);
        }
    }

    public void updateVenueView(Venue venue) {
        TextView textView = (TextView) getActivity().findViewById(R.id.cardTextViewName);
        textView.setText(venue.getName());
        textView = (TextView) getActivity().findViewById(R.id.cardTextViewAddress);
        textView.setText(venue.getAddress());
        textView = (TextView) getActivity().findViewById(R.id.cardTextViewCity);
        textView.setText(venue.getCityStateZip());
        if(null != venue.getImageUrl()  && !"".equals(venue.getImageUrl())) {
            ImageView imageView = (ImageView) getActivity().findViewById(R.id.cardImageView);
            Context context = getActivity().getBaseContext();
            Picasso.with(context)
                    .load(venue.getImageUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                   // .resize(96, 96)
                  //  .centerCrop()
                    .into(imageView);
        }
        mVenue = venue;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mVenue = (Venue) savedInstanceState.getSerializable(SER_KEY);
        }

        return inflater.inflate(R.layout.fragment_venue_detail, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SER_KEY,mVenue);
    }
}
