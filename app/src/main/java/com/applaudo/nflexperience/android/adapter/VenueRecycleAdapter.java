package com.applaudo.nflexperience.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applaudo.nflexperience.android.R;
import com.applaudo.nflexperience.android.model.Venue;
import com.applaudo.nflexperience.android.util.ImageUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 28/05/2015.
 */
public class VenueRecycleAdapter extends RecyclerView.Adapter<VenueViewHolder> {

    private OnVenueSelectedListener mListener;
    private List<Venue> venueDataSet;

    public VenueRecycleAdapter() {
        venueDataSet = new ArrayList<>();
    }

    public void setOnVenueSelectedListener(OnVenueSelectedListener listener) {
        mListener = listener;
    }

    public List<Venue> getVenueDataSet() {
        return this.venueDataSet;
    }

    @Override
    public VenueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_venue, parent, false);
        return new VenueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VenueViewHolder holder, final int position) {
        final Venue venue = venueDataSet.get(position);
        holder.textViewName.setText(venue.getName());
        holder.textViewAddress.setText(venue.getAddress());
        holder.textViewCity.setText(venue.getCityStateZip());
        if (null != venue.getImageUrl() && !"".equals(venue.getImageUrl())) {
            Context context = holder.imageView.getContext();

            int px = ImageUtil.convertDpToPixel(40, context);

            Picasso.with(context)
                    .load(venue.getImageUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .resize(px, px)
                    .centerCrop()
                    .into(holder.imageView);
        } else {
            //this is done to make sure to show a placeholder if the image does not have
            //an image.
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
        }

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onVenueSelected(venue);
            }
        });
    }

    @Override
    public int getItemCount() {
        return venueDataSet.size();
    }

    public interface OnVenueSelectedListener {
        void onVenueSelected(Venue venue);
    }
}
