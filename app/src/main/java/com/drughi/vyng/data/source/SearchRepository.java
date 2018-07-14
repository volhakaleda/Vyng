package com.drughi.vyng.data.source;

import android.support.annotation.NonNull;

import com.drughi.vyng.data.VyngService;
import com.drughi.vyng.data.model.DataItem;
import com.drughi.vyng.data.model.GifMutable;
import com.drughi.vyng.data.model.SearchResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class SearchRepository {

    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;

    @Inject
    public SearchRepository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public Single<List<GifMutable>> loadVideos(@NonNull final String searchText, final boolean isNewTerm) {
        List<GifMutable> cachedGifs = localDataSource.getCachedGifs(searchText);
        if(cachedGifs != null && !cachedGifs.isEmpty() && !isNewTerm) {
            return Single.just(cachedGifs);
        }
        return remoteDataSource.loadVideos(searchText);
    }

    public long updateVoteCount(final long id, final boolean isUp) {
        return localDataSource.updateVoteCount(id, isUp);
    }

}
