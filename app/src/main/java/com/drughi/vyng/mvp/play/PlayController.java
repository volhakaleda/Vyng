package com.drughi.vyng.mvp.play;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.drughi.vyng.R;
import com.drughi.vyng.VyngApp;
import com.drughi.vyng.util.ArgBuilder;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PlayController extends Controller {

    @BindView(R.id.video_view)
    PlayerView playerView;

    @Inject
    ExoPlayer player;

    private static final String VYNG_PLAYER = "exoplayer-vyng";
    private static final String KEY_URL = "keyUrl";

    private final String url;
    private Unbinder unbinder;

    public PlayController(final String url) {
        this(new ArgBuilder(new Bundle())
                .putString(KEY_URL, url)
                .build());
    }

    PlayController(Bundle args) {
        super(args);
        url = getArgs().getString(KEY_URL);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.play_controller, container, false);
        ((VyngApp) getActivity().getApplication()).getAppComponent().inject(this);

        unbinder = ButterKnife.bind(this, view);
        initializePlayer();

        return view;
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        player.setPlayWhenReady(false);
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

    private void initializePlayer() {
        playerView.setPlayer(player);

        player.setPlayWhenReady(true);

        Uri uri = Uri.parse(url);
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
