package com.drughi.vyng.data;

import com.drughi.vyng.data.model.apiUnmutableModels.SearchResponse;


import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit Service
 */
public interface VyngService {

    /**
     *
     * Things to improve:
     *
     * 1. The key needs to be encrytpted and stored in the keystore. For the sake of time saving,
     * the key is hardcoded in this example
     */
    @GET("/v1/gifs/search?q=&api_key=FZXcJkFBFzmIL5z6J7IEav83PSmcMo8L")
    Single<SearchResponse> getVideos(@Query("q") String text);
}
