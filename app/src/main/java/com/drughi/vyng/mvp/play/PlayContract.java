package com.drughi.vyng.mvp.play;


public interface PlayContract {

    interface View {

        void showUpdatedUpVoteCount(final long newVoteCount);

        void showUpdatedDownVoteCount(final long newVoteCount);

        void showErrorMessage();
    }

    interface Presenter {

        void updateVoteCount(final long gifId, final boolean isUpVote);

        void unsubscribe();
    }
}
