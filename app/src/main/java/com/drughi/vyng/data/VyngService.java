package com.drughi.vyng.data;

import com.drughi.vyng.data.model.SearchResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VyngService {

    //TODO: keys needs to be encrytpted and stored in the keystore. For the sake of time saving,
    //TODO: the key is hardcoded in this example
    @GET("/v1/gifs/search?q=&api_key=FZXcJkFBFzmIL5z6J7IEav83PSmcMo8L")
    Single<SearchResponse> getVideos(@Query("q") String text);
}
