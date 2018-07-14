package com.drughi.vyng.mvp.play;

import com.drughi.vyng.data.model.GifMutable;
import com.drughi.vyng.data.source.Repository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Presenter to handle vote actions.
 */
public class PlayPresenter implements PlayContract.Presenter {

    private PlayContract.View view;
    private Repository repo;
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

    /**
     * Sends event to {@link Repository} to update vote count.
     *
     * @param gifId - gif id
     * @param isUpVote - the flag indication whether upvote or downvote action took place
     */
    @Override
    public void updateVoteCount(long gifId, final boolean isUpVote) {

        DisposableSingleObserver<Long> countDisposable = new DisposableSingleObserver<Long>() {

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

    /**
     * Sends event to {@link Repository} to get the number of upvotes and downvotes.
     *
     * @param gifId - gif id
     */
    @Override
    public void loadGifCount(final long gifId) {

        DisposableSingleObserver<GifMutable> gifDisposable = new DisposableSingleObserver<GifMutable>() {

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
     * Clears all disposables to avoid memory leaks.
     */
    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

}
