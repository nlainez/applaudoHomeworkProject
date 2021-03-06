package com.applaudo.nflexperience.android;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.applaudo.nflexperience.android.adapter.VenueRecycleAdapter;
import com.applaudo.nflexperience.android.fragment.VenueDetailFragment;
import com.applaudo.nflexperience.android.fragment.VenueListFragment;
import com.applaudo.nflexperience.android.model.Venue;

public class MainActivity extends AppCompatActivity
        implements VenueRecycleAdapter.OnVenueSelectedListener,
        VenueDetailFragment.OnShareButtonClickedListener,
        VenueDetailFragment.OnMapButtonClickedListener,
        VenueDetailFragment.OnBuyButtonClickedListener {

    private static final String BACKSTACK_NAME = "venueDetailFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) {

                //If theres more than one fragment in the backstack we must turn on the
                //home button to provide ancestral navigation, only if we are returning
                //from orientation change
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
                return;
            }

            VenueListFragment venueListFragment = VenueListFragment.newInstance();

            venueListFragment.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, venueListFragment).commit();
        } else {

            //this is a workaround to hide the detail fragment on start
            //Its not pretty an certainly can be improved
            //actually, this produces a bug when you start the app and change orientation
            if (savedInstanceState == null) {
                VenueDetailFragment detailFragment = (VenueDetailFragment)
                        getSupportFragmentManager().findFragmentById(R.id.venueDetail_fragment);

                getSupportFragmentManager().beginTransaction()
                        .hide(detailFragment).commit();
            }
        }

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (item.getItemId() == android.R.id.home) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            this.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onVenueSelected(Venue venue) {

        VenueDetailFragment articleFrag = (VenueDetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.venueDetail_fragment);

        if (articleFrag != null) {

            //we are in 2 pane mode...

            if (articleFrag.isHidden()) {
                getSupportFragmentManager().beginTransaction()
                        .show(articleFrag).commit();
            }

            articleFrag.updateVenueView(venue);

        } else {

            VenueDetailFragment newFragment = VenueDetailFragment.newInstance();
            Bundle args = new Bundle();
            args.putSerializable(VenueDetailFragment.SER_KEY, venue);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(BACKSTACK_NAME);
            transaction.commit();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void onShareButtonClicked(Venue venue) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareText = "Join me at " + venue.getName() + "! Best venue in sports ever!"
                + " Just drive to " + venue.getAddress();
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Looking for the best venue in sports?");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    @Override
    public void onMapButtonClicked(Venue venue) {

        //please see: https://developers.google.com/maps/documentation/android/intents for
        //further reference. Im doing this to allow the user to use Maps to locate the venue
        Uri gmmIntentUri = Uri.parse("geo:" + venue.getLatitude() + "," + venue.getLongitude())
                .buildUpon()
                .appendQueryParameter("q", venue.getAddress() + ", " + venue.getCityStateZip())
                .build();
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    @Override
    public void onBuyButtonClicked(Venue venue) {
        if (null != venue.getTicketLink() && !"".equals(venue.getTicketLink())) {
            //if theres a ticket link, allow user to check the site in default browser
            Uri ticketLink = Uri.parse(venue.getTicketLink());
            Intent intent = new Intent(Intent.ACTION_VIEW, ticketLink);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        } else {
            Context context = getApplicationContext();
            CharSequence text = "No ticket link available";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}
