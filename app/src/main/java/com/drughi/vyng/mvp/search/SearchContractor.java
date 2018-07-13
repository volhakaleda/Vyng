package com.drughi.vyng.mvp.search;

import com.drughi.vyng.data.model.DataItem;
import com.drughi.vyng.data.model.GifMutable;

import java.util.List;

public interface SearchContractor {

  interface View {

    void showVideos(List<GifMutable> data);

    void showErrorMessage();
  }

  interface Presenter {

    void loadVideos(final String searchTerm, final boolean isNewTerm);

    void unsubscribe();
  }
}
