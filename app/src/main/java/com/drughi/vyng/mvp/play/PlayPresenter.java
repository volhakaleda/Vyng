package com.drughi.vyng.mvp.play;

import com.drughi.vyng.data.model.GifMutable;
import com.drughi.vyng.data.source.Repository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class PlayPresenter implements PlayContract.Presenter {

    private PlayContract.View view;
    private Repository repo;
    private DisposableSingleObserver<Long> countDisposable;
    private DisposableSingleObserver<GifMutable> gifDisposable;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public PlayPresenter(Repository repo) {
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

        countDisposable = new DisposableSingleObserver<Long>() {

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

        compositeDisposable.add(countDisposable);

        repo.updateVoteCount(gifId, isUpVote)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(countDisposable);
    }

    @Override
    public void loadGifCount(final long gifId) {

        gifDisposable = new DisposableSingleObserver<GifMutable>() {

            @Override
            public void onSuccess(GifMutable gif) {
                view.showUpdatedUpVoteCount(gif.getUpVotes());
                view.showUpdatedDownVoteCount(gif.getDownVotes());
            }

            @Override
            public void onError(Throwable throwable) {
                view.showErrorMessage();
            }

        };

        compositeDisposable.add(gifDisposable);

        repo.getCachedGif(gifId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gifDisposable);
    }

    /**
     * Clear all disposables to avoid memory leaks
     */
    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

}
