package com.drughi.vyng.mvp.play;

import com.drughi.vyng.data.source.SearchRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class PlayPresenter implements PlayContract.Presenter {

    private PlayContract.View view;
    private SearchRepository repo;
    private DisposableSingleObserver<Long> disposable;

    @Inject
    public PlayPresenter(SearchRepository repo) {
        this.repo = repo;
    }

    /**
     * Connects {@link PlayContract.View} to the presenter
     *
     * @param view
     */
    public void setView(PlayContract.View view) {
        this.view = view;
    }

    @Override
    public void updateVoteCount(long gifId, final boolean isUpVote) {

        disposable = new DisposableSingleObserver<Long>() {

            @Override
            public void onSuccess(Long count) {
                if (isUpVote) {
                    view.showUpdatedUpVoteCount(count);
                } else {
                    view.showUpdatedDownVoteCount(count);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                view.showErrorMessage();
            }

        };

        repo.updateVoteCount(gifId, isUpVote)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposable);
    }

    /**
     * Unsubscribe from update votes subscription to avoid memory leaks
     */
    @Override
    public void unsubscribe() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

}
