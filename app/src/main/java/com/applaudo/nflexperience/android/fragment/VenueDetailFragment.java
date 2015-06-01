package com.applaudo.nflexperience.android.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.applaudo.nflexperience.android.R;
import com.applaudo.nflexperience.android.model.ScheduleItem;
import com.applaudo.nflexperience.android.model.Venue;
import com.squareup.picasso.Picasso;

public class VenueDetailFragment extends Fragment {

    public final static String SER_KEY = "com.applaudo.nflexperience.android.model";

    private OnShareButtonClickedListener mShareListener;
    private OnMapButtonClickedListener mMapListener;
    private OnBuyButtonClickedListener mBuyListener;

    private Venue mVenue;

    public static VenueDetailFragment newInstance() {
        return new VenueDetailFragment();
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
        ImageView imageView = (ImageView) getActivity().findViewById(R.id.cardImageView);
        if (null != venue.getImageUrl() && !"".equals(venue.getImageUrl())) {

            Context context = getActivity().getBaseContext();
            Picasso.with(context)
                    .load(venue.getImageUrl())
                    .placeholder(R.drawable.venue_placeholder_img)
                    .error(R.drawable.venue_error_img)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.venue_error_img);
        }

        if (!venue.getSchedule().isEmpty()) {

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
        try {
            mBuyListener = (OnBuyButtonClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnBuyButtonClickedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mShareListener = null;
        mMapListener = null;
        mBuyListener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mVenue = (Venue) savedInstanceState.getSerializable(SER_KEY);
        }

        View view = inflater.inflate(R.layout.fragment_venue_detail, container, false);

        Button button = (Button) view.findViewById(R.id.cardShareButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mShareListener.onShareButtonClicked(mVenue);
            }
        });

        button = (Button) view.findViewById(R.id.cardMapsButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMapListener.onMapButtonClicked(mVenue);
            }
        });

        button = (Button) view.findViewById(R.id.cardBuyTicketButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBuyListener.onBuyButtonClicked(mVenue);
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

    public interface OnBuyButtonClickedListener {
        void onBuyButtonClicked(Venue venue);
    }


}
