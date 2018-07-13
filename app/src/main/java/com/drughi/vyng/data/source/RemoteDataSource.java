package com.drughi.vyng.data.source;

import android.support.annotation.NonNull;

import com.drughi.vyng.data.VyngService;
import com.drughi.vyng.data.model.DataItem;
import com.drughi.vyng.data.model.GifMutable;
import com.drughi.vyng.data.model.Original;
import com.drughi.vyng.data.model.SearchResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class RemoteDataSource {

    private VyngService service;
    private BoxStore boxStore;

    @Inject
    public RemoteDataSource(VyngService service, BoxStore boxStore) {
        this.service = service;
        this.boxStore = boxStore;
    }

    public Single<List<GifMutable>> loadVideos(@NonNull final String searchText) {
        return service.getVideos(searchText)
                .map(new Function<SearchResponse, List<GifMutable>>() {
            @Override
            public List<GifMutable> apply(SearchResponse searchResponse) throws Exception {
                List<DataItem> gifs = searchResponse.getData();
                return cacheGifsLocally(gifs);
            }
        });
    }

    private List<GifMutable> cacheGifsLocally(List<DataItem> gifs) {
        List<GifMutable> mutables = new ArrayList<>();
        for(DataItem apiGif : gifs) {
            Original image = apiGif.getImages().getOriginal();
            GifMutable mutable = new GifMutable(image.getUrl(), image.getMp4());
            mutables.add(mutable);
        }

        Box<GifMutable> gifBox = boxStore.boxFor(GifMutable.class);
        gifBox.put(mutables);
        return mutables;
    }
}
