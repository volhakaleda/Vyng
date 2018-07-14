package com.drughi.vyng.mvp.play;

/**
 * Contract that defines methods to be implemeted by View and Presenter
 */
public interface PlayContract {

    interface View {

        void showUpdatedUpVoteCount(final long newVoteCount);

        void showUpdatedDownVoteCount(final long newVoteCount);

        void showErrorMessage();
    }

    interface Presenter {

        void updateVoteCount(final long gifId, final boolean isUpVote);

        void loadGifCount(final long gifId);

        void unsubscribe();
    }
}
