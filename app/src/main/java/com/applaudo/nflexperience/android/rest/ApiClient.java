package com.applaudo.nflexperience.android.rest;

import retrofit.RestAdapter;

/**
 * Created by Carlos on 29/05/2015.
 */
public class ApiClient {

    private final static String API_URL = "https://s3.amazonaws.com/jon-hancock-phunware";

    private static PhunwareNflApi mPhunwareNflApi;

    public static PhunwareNflApi getInstance() {
        if (mPhunwareNflApi == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_URL)
                    .build();

            mPhunwareNflApi = restAdapter.create(PhunwareNflApi.class);
        }

        return mPhunwareNflApi;
    }

}
