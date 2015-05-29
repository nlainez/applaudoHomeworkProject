package com.applaudo.nflexperience.android;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VenueDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VenueDetailFragment extends Fragment {

    final static String ARG_POSITION = "position";
    private int mCurrentPosition = -1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
            updateVenueView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            updateVenueView(mCurrentPosition);
        }
    }

    public void updateVenueView(int position) {
        TextView textView = (TextView) getActivity().findViewById(R.id.texto);
        textView.setText("Dummy");
        mCurrentPosition = position;
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
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        return inflater.inflate(R.layout.fragment_venue_detail, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }
}
