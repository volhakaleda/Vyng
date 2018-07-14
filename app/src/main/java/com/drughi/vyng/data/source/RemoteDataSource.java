package com.drughi.vyng.data.source;

import android.support.annotation.NonNull;

import com.drughi.vyng.data.VyngService;
import com.drughi.vyng.data.model.apiUnmutableModels.DataItem;
import com.drughi.vyng.data.model.GifMutable;
import com.drughi.vyng.data.model.apiUnmutableModels.Original;
import com.drughi.vyng.data.model.apiUnmutableModels.SearchResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.reactivex.Single;
import io.reactivex.functions.Function;

/**
 * Remote data source of gifs.
 */
public class RemoteDataSource {

    private VyngService service;
    private BoxStore boxStore;

    @Inject
    public RemoteDataSource(VyngService service, BoxStore boxStore) {
        this.service = service;
        this.boxStore = boxStore;
    }

    /**
     * Connects to the API to pull list of gifs
     *
     * @param searchText - user input search term
     *
     * @return Single of mutable gif list
     */
    public Single<List<GifMutable>> loadVideos(@NonNull final String searchText) {
        return service.getVideos(searchText)
                .map(new Function<SearchResponse, List<GifMutable>>() {
            @Override
            public List<GifMutable> apply(SearchResponse searchResponse) throws Exception {
                List<DataItem> gifs = searchResponse.getData();
                return cacheGifsLocally(gifs, searchText);
            }
        });
    }

    /**
     * Transforms a list of raw API gifs to {@link GifMutable} and caches them in {@link LocalDataSource}
     *
     * Things to improve:
     *
     * 1. In this app we rely on the original size. The right approach is to pull the sizes
     * dynamically depending on the device configs
     *
     * @param gifs - list of raw API gif models
     * @param searchText - user input search term
     * @return list of transformed gif models {@link GifMutable}
     */
    @NonNull
    private List<GifMutable> cacheGifsLocally(@NonNull final List<DataItem> gifs,
                                              @NonNull final String searchText) {
        List<GifMutable> mutables = new ArrayList<>();
        for(DataItem apiGif : gifs) {
            Original image = apiGif.getImages().getOriginal();
            GifMutable mutable = new GifMutable(image.getUrl(), image.getMp4(), searchText);
            mutables.add(mutable);
        }

        Box<GifMutable> gifBox = boxStore.boxFor(GifMutable.class);
        gifBox.put(mutables);
        return mutables;
    }
}
