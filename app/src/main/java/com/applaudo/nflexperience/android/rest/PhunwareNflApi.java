package com.applaudo.nflexperience.android.rest;

import com.applaudo.nflexperience.android.model.Venue;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Carlos on 29/05/2015.
 */
public interface PhunwareNflApi {

    @GET("/nflapi-static.json")
    void getVenues(Callback<List<Venue>> callback);

}
