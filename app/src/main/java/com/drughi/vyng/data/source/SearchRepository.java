package com.drughi.vyng.data.source;

import android.support.annotation.NonNull;

import com.drughi.vyng.data.VyngService;
import com.drughi.vyng.data.model.SearchResponse;

import javax.inject.Inject;

import io.reactivex.Single;

public class SearchRepository {

    private VyngService service;

    @Inject
    public SearchRepository(VyngService service) {
        this.service = service;
    }

    public Single<SearchResponse> loadVideos(@NonNull final String searchText) {
        return service.getVideos(searchText);
    }
}
