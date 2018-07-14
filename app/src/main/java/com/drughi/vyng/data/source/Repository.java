package com.drughi.vyng.data.source;

import android.support.annotation.NonNull;

import com.drughi.vyng.data.model.GifMutable;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class Repository {

    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;

    @Inject
    public Repository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public Single<List<GifMutable>> loadVideos(@NonNull final String searchText) {
        List<GifMutable> cachedGifs = localDataSource.getCachedGifs(searchText);
        if(cachedGifs != null && !cachedGifs.isEmpty()) {
            return Single.just(cachedGifs);
        }
        return remoteDataSource.loadVideos(searchText);
    }

    public Single<Long> updateVoteCount(final long id, final boolean isUp) {
        long count = localDataSource.updateVoteCount(id, isUp);
        return Single.just(count);
    }

    public Single<GifMutable> getCachedGif(final long id) {
        GifMutable gif = localDataSource.getCachedGif(id);
        return Single.just(gif);
    }

}
