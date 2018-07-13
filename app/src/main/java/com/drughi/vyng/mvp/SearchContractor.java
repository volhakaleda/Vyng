package com.drughi.vyng.mvp;

public interface SearchContractor {

  interface View {

    void showVideos();

    void showErrorMessage();
  }

  interface Presenter {

    void loadVideos(final String searchTerm);

  }
}
