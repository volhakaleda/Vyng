package com.drughi.vyng.data.source;

import android.support.annotation.NonNull;

import com.drughi.vyng.data.model.GifMutable;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Repository that routes logic to either {@link LocalDataSource} or {@link RemoteDataSource} to
 * pull the data.
 */
public class Repository {

    private LocalDataSource localDataSource;
    private RemoteDataSource remoteDataSource;

    @Inject
    public Repository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    /**
     * Retrieves data from {@link LocalDataSource} if available, otherwise from {@link RemoteDataSource}.
     *
     * @param searchText - user input search term
     * @return list of mutable gifs to be displayed to the user
     */
    @NonNull
    public Single<List<GifMutable>> loadVideos(@NonNull final String searchText) {
        List<GifMutable> cachedGifs = localDataSource.getCachedGifs(searchText);
        if(cachedGifs != null && !cachedGifs.isEmpty()) {
            return Single.just(cachedGifs);
        }
        return remoteDataSource.loadVideos(searchText);
    }

    /**
     * Updates the count of upvotes or downvotes in {@link LocalDataSource}
     *
     * @param id - gif id
     * @param isUp - the flag indicating whether upvote or downvote happened
     * @return return the updated count of an up/downvote
     */
    public Single<Long> updateVoteCount(final long id, final boolean isUp) {
        long count = localDataSource.updateVoteCount(id, isUp);
        return Single.just(count);
    }

    /**
     * Retrieves a cached gif from {@link LocalDataSource}.
     *
     * @param id - gif id
     * @return a cached gif
     */
    public Single<GifMutable> getCachedGif(final long id) {
        GifMutable gif = localDataSource.getCachedGif(id);
        return Single.just(gif);
    }

}
