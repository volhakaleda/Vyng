package com.drughi.vyng.data.source;

import com.drughi.vyng.data.model.GifMutable;

import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;

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

    public long updateVoteCount(final long id, final boolean isUp) {
        Box<GifMutable> gifBox = boxStore.boxFor(GifMutable.class);
        GifMutable mutable = gifBox.get(id);
        long newCount;

        if(isUp) {
            newCount = mutable.getUpVotes() + 1;
            mutable.setUpVotes(newCount);
        } else {
            newCount = mutable.getDownVotes() + 1;
            mutable.setDownVotes(newCount);
        }

        gifBox.put(mutable);
        return newCount;
    }

}
