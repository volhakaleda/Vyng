package com.drughi.vyng.data.source;

import com.drughi.vyng.data.model.GifMutable;
import com.drughi.vyng.data.model.GifMutable_;

import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;
import io.objectbox.BoxStore;

/**
 * Local storage of the most recently searched gifs.
 */
public class LocalDataSource {

    private BoxStore boxStore;

    @Inject
    public LocalDataSource(BoxStore boxStore) {
        this.boxStore = boxStore;
    }

    /**
     * Pulls all cached gifs related to user's search input.
     *
     * @param searchText - the most recently typed in search term used to pull all the relevant gifs
     *                  from the API
     *
     * @return a list of gifs that have been mutated to be displayed in
     * {@link com.drughi.vyng.mvp.search.SearchContractor.View}
     */
    public List<GifMutable> getCachedGifs(final String searchText) {
        Box<GifMutable> gifBox = boxStore.boxFor(GifMutable.class);
        List<GifMutable> cachedGifs = gifBox.query().equal(GifMutable_.searchText, searchText)
                .build()
                .find();
        return cachedGifs;
    }


    /**
     * Updates the count of down/upvotes for a gif.
     *
     * @param id - gif id
     * @param isUp - flag indicating whether up or down vote action took place
     * @return new vote count
     */
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
