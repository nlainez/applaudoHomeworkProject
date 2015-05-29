package com.applaudo.nflexperience.android;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Carlos on 28/05/2015.
 */
public class VenueViewHolder extends RecyclerView.ViewHolder{

    private final View mParent;
    protected TextView textView;

    public VenueViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.textViewRc);
        mParent = itemView;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        mParent.setOnClickListener(listener);
    }
}
