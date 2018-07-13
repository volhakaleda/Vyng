package com.drughi.vyng.data.source;

import com.drughi.vyng.data.model.DataItem;
import com.drughi.vyng.data.model.GifMutable;

import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.reactivex.Flowable;

public class LocalDataSource {

    private BoxStore boxStore;

    @Inject
    public LocalDataSource(BoxStore boxStore) {
        this.boxStore = boxStore;
    }

    public List<GifMutable> getCachedGifs() {
        Box<GifMutable> gifBox = boxStore.boxFor(GifMutable.class);
        List<GifMutable> cachedGifs = gifBox.query().build().find();
        return cachedGifs;
    }
}
