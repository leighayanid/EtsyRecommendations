package com.etsy.sample.api;

import com.etsy.sample.model.ActiveListings;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by user on 3/16/2016.
 */
public interface Api {

    //method for api call
    //GET is the protocol, uri is listings/active which can be found on etsy documentation
    @GET("/listings/active")
    void activeListings(@Query("includes") String includes, Callback<ActiveListings> callback); //to include optional parameters and annotate

}
