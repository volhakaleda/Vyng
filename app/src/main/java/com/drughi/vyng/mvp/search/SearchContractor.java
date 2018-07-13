package com.drughi.vyng.mvp.search;

import com.drughi.vyng.data.model.DataItem;

import java.util.List;

public interface SearchContractor {

  interface View {

    void showVideos(List<DataItem> data);

    void showErrorMessage();
  }

  interface Presenter {

    void loadVideos(final String searchTerm);

    void unsubscribe();
  }
}
