package com.drughi.vyng.mvp.search;

import com.drughi.vyng.data.model.GifMutable;
import com.drughi.vyng.data.source.Repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Presenter that connects to the {@link Repository} to load gifs
 */
class SearchPresenter implements SearchContract.Presenter {

  private SearchContract.View view;
  private Repository repo;
  private DisposableSingleObserver<List<GifMutable>> disposable;

  @Inject
  public SearchPresenter(Repository repo) {
    this.repo = repo;
  }

  /**
   * Connects {@link SearchContract.View} to the presenter
   * @param view
   */
  public void setView(SearchContract.View view) {
    this.view = view;
  }

  /**
   *  Loads a paginated list of related gifs based on user search term.
   *
   * @param searchTerm - user input
   */
  @Override
  public void loadVideos(final String searchTerm) {
    disposable = new DisposableSingleObserver<List<GifMutable>>() {

      @Override
      public void onSuccess(List<GifMutable> gifs) {
        view.showVideos(gifs);
      }

      @Override
      public void onError(Throwable throwable) {
        view.showErrorMessage();
      }

    };

    repo.loadVideos(searchTerm)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(disposable);
  }

  /**
   * Unsubscribe from search subscription to avoid memory leaks.
   */
  @Override
  public void unsubscribe() {
    if (disposable != null && !disposable.isDisposed()) {
      disposable.dispose();
    }
  }

}
