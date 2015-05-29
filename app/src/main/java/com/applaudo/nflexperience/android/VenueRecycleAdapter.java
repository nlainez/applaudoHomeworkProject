package com.applaudo.nflexperience.android;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applaudo.nflexperience.android.dummy.DummyContent;

import java.util.List;

/**
 * Created by Carlos on 28/05/2015.
 */
public class VenueRecycleAdapter extends RecyclerView.Adapter<VenueViewHolder>{

    private static final int STARTING_VENUES = 5;
    private OnVenueSelectedListener mListener;
    List<DummyContent.DummyItem> venueDataSet;

    public VenueRecycleAdapter() {
        venueDataSet = DummyContent.ITEMS;
    }

    public void setOnVenueSelectedListener(OnVenueSelectedListener listener) {
        mListener = listener;
    }

    @Override
    public VenueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_venue, parent, false);
        return new VenueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VenueViewHolder holder, final int position) {
        DummyContent.DummyItem di = venueDataSet.get(position);
        holder.textView.setText(di.content);
        holder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mListener.onVenueSelected(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return venueDataSet.size();
    }

    public interface OnVenueSelectedListener {
        public void onVenueSelected(int position);
    }
}
