package com.drughi.vyng.mvp.play;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bluelinelabs.conductor.Controller;
import com.drughi.vyng.R;
import com.drughi.vyng.VyngApp;
import com.drughi.vyng.data.model.GifMutable;
import com.drughi.vyng.util.ArgBuilder;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * View that plays a gif and provides info about upvotes and downvotes.
 *
 * Things to improve:
 * 1. In landscape mode the player covers upvote/downvote counts. UX need to be improved.
 * 2. Scale the video proportionally.
 */
public class PlayController extends Controller implements PlayContract.View{

    @BindView(R.id.video_view)
    PlayerView playerView;

    @BindView(R.id.thumb_up)
    TextView thumbUp;

    @BindView(R.id.thumb_down)
    TextView thumbDown;

    @Inject
    ExoPlayer player;

    @Inject
    PlayPresenter presenter;

    private static final String VYNG_PLAYER = "exoplayer-vyng";
    private static final String KEY_CLICKED_GIF = "keyClickedGif";

    private final GifMutable clickedGif;
    private Unbinder unbinder;

    public PlayController(final GifMutable gif) {
        this(new ArgBuilder(new Bundle())
                .putParcelable(KEY_CLICKED_GIF, gif)
                .build());
    }

    public PlayController(Bundle args) {
        super(args);
        clickedGif = getArgs().getParcelable(KEY_CLICKED_GIF);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.play_controller, container, false);
        ((VyngApp) getActivity().getApplication()).getAppComponent().inject(this);

        unbinder = ButterKnife.bind(this, view);
        presenter.setView(this);
        presenter.loadGifCount(clickedGif.getId());
        initializePlayer();
        return view;
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        if(player != null) {
            player.setPlayWhenReady(false);
        }
        presenter.unsubscribe();
    }

    @Override
    protected void onDestroyView(@NonNull View view) {
        super.onDestroyView(view);
        unbinder.unbind();
        unbinder = null;
    }

    @Override
    protected void onActivityPaused(@NonNull Activity activity) {
        super.onActivityPaused(activity);
        releasePlayer();
    }

    @OnClick(R.id.thumb_up)
    public void countUp() {
        presenter.updateVoteCount(clickedGif.getId(), true);
    }

    @OnClick(R.id.thumb_down)
    public void countDown() {
        presenter.updateVoteCount(clickedGif.getId(), false);
    }

    @Override
    public void showUpdatedUpVoteCount(final long newVoteCount) {
        thumbUp.setText(String.valueOf(newVoteCount));
    }

    @Override
    public void showUpdatedDownVoteCount(final long newVoteCount) {
        thumbDown.setText(String.valueOf(newVoteCount));
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(getActivity(), R.string.general_error_message, Toast.LENGTH_LONG).show();
    }

    private void initializePlayer() {
        playerView.setPlayer(player);

        player.setPlayWhenReady(true);

        Uri uri = Uri.parse(clickedGif.getMp4());
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory(VYNG_PLAYER))
                .createMediaSource(uri);
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;
        }
    }
}
