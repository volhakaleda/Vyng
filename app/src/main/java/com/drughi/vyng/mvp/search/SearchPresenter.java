package com.drughi.vyng.mvp.search;

import com.drughi.vyng.data.model.DataItem;
import com.drughi.vyng.data.model.GifMutable;
import com.drughi.vyng.data.source.SearchRepository;

import org.reactivestreams.Subscriber;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Presenter that connects to the {@link SearchRepository} to load gifs
 */
class SearchPresenter implements SearchContractor.Presenter {

  private SearchContractor.View view;
  private SearchRepository repo;
  private DisposableSingleObserver<List<GifMutable>> disposable;

  @Inject
  public SearchPresenter(SearchRepository repo) {
    this.repo = repo;
  }

  /**
   * Connects {@link SearchContractor.View} to the presenter
   * @param view
   */
  public void setView(SearchContractor.View view) {
    this.view = view;
  }

  /**
   *  Loads paginated list of related gifs based on user search term
   *
   * @param searchTerm - user input
   */
  @Override
  public void loadVideos(final String searchTerm, final boolean isNewTerm) {
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

    repo.loadVideos(searchTerm, isNewTerm)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(disposable);
  }

  /**
   * Unsubscribe from search subscription to avoid memory leaks
   */
  @Override
  public void unsubscribe() {
    if (disposable != null && !disposable.isDisposed()) {
      disposable.dispose();
    }
  }

}