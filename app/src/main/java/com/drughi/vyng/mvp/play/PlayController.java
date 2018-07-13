package com.drughi.vyng.mvp.play;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.drughi.vyng.R;
import com.drughi.vyng.VyngApp;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PlayController extends Controller {

    private Unbinder unbinder;

    @Inject
    public PlayController() {
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.play_controller, container, false);
        ((VyngApp) getActivity().getApplication()).getAppComponent().inject(this);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void onDestroyView(@NonNull View view) {
        super.onDestroyView(view);
        unbinder.unbind();
        unbinder = null;
    }
}
