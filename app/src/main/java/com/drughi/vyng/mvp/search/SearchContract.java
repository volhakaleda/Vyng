package com.drughi.vyng.mvp.search;

import com.drughi.vyng.data.model.GifMutable;

import java.util.List;

/**
 * Contract that defines methods to be implemented by View and Presenter
 */
public interface SearchContract {

  interface View {

    void showVideos(List<GifMutable> gifs);

    void showErrorMessage();
  }

  interface Presenter {

    void loadVideos(final String searchTerm);

    void unsubscribe();
  }
}
