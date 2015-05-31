package com.applaudo.nflexperience.android;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.applaudo.nflexperience.android.model.ScheduleItem;
import com.applaudo.nflexperience.android.model.Venue;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VenueDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VenueDetailFragment extends Fragment{

    final static String ARG_VENUE = "venue";
    final static String SER_KEY = "com.applaudo.nflexperience.android.model";

    private OnShareButtonClickedListener mShareListener;
    private OnMapButtonClickedListener mMapListener;

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
                    .placeholder(R.drawable.venue_placeholder_img)
                    .error(R.drawable.venue_error_img)
                    .into(imageView);
        }

        if(!venue.getSchedule().isEmpty()) {

            StringBuilder sb = new StringBuilder();

            for (ScheduleItem scheduleItem : venue.getSchedule()) {
                sb.append(scheduleItem.getDecoratedDate())
                        .append(System.getProperty("line.separator"))
                        .append(System.getProperty("line.separator"));
            }

            textView = (TextView) getActivity().findViewById(R.id.cardTextViewSchedule);
            textView.setText(sb.toString());

        }

        mVenue = venue;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mShareListener = (OnShareButtonClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnShareButtonClickedListener");
        }
        try {
            mMapListener = (OnMapButtonClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMapButtonClickedListener");
        }
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

        View view = inflater.inflate(R.layout.fragment_venue_detail, container, false);

        Button button = (Button) view.findViewById(R.id.cardShareButton);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mShareListener.onShareButtonClicked(mVenue);
            }
        });

        button = (Button) view.findViewById(R.id.cardMapsButton);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mMapListener.onMapButtonClicked(mVenue);
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SER_KEY, mVenue);
    }

    public interface OnShareButtonClickedListener {
        void onShareButtonClicked(Venue venue);
    }

    public interface OnMapButtonClickedListener {
        void onMapButtonClicked(Venue venue);
    }


}
