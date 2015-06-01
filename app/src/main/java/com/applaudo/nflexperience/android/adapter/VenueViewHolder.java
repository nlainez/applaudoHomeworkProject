package com.applaudo.nflexperience.android.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.applaudo.nflexperience.android.R;

/**
 * Created by Carlos on 28/05/2015.
 */
public class VenueViewHolder extends RecyclerView.ViewHolder {

    private final View mParent;
    protected TextView textViewName;
    protected TextView textViewAddress;
    protected TextView textViewCity;
    protected ImageView imageView;

    public VenueViewHolder(View itemView) {
        super(itemView);
        textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        textViewAddress = (TextView) itemView.findViewById(R.id.textViewAddress);
        textViewCity = (TextView) itemView.findViewById(R.id.textViewCity);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
        mParent = itemView;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        mParent.setOnClickListener(listener);
    }
}
