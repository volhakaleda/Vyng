package com.drughi.vyng.mvp;

import com.drughi.vyng.data.SearchRepository;

import javax.inject.Inject;

class SearchPresenter implements SearchContractor.Presenter {

  private SearchContractor.View view;
  private SearchRepository repo;

  @Inject
  public SearchPresenter(SearchRepository repo) {
    this.repo = repo;
  }

  void unsubscribe() {
  }

  public void setView(SearchContractor.View view) {
    this.view = view;
  }

  @Override
  public void loadVideos(String searchTerm) {

  }
}
