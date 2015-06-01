package com.applaudo.nflexperience.android.rest;

import com.applaudo.nflexperience.android.model.ScheduleItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Carlos on 29/05/2015.
 */
public class ApiClient {

    private final static String API_URL = "https://s3.amazonaws.com/jon-hancock-phunware";

    private static PhunwareNflApi mPhunwareNflApi;

    public static PhunwareNflApi getInstance() {
        if (mPhunwareNflApi == null) {
            //we set the date format to avoid parsing errors
            Gson gson = new GsonBuilder()
                    .setDateFormat(ScheduleItem.DATE_FORMAT)
                    .create();
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_URL)
                    .setConverter(new GsonConverter(gson))
                    .build();

            mPhunwareNflApi = restAdapter.create(PhunwareNflApi.class);
        }

        return mPhunwareNflApi;
    }

}
