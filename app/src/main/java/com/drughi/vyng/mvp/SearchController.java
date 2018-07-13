package com.drughi.vyng.mvp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bluelinelabs.conductor.Controller;
import com.drughi.vyng.R;
import com.drughi.vyng.VyngApp;
import com.drughi.vyng.adapters.SearchAdapter;
import com.drughi.vyng.data.model.DataItem;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SearchController extends Controller implements SearchContractor.View {

    @BindView(R.id.rec)
    RecyclerView rec;

    @BindView(R.id.input)
    EditText search;

    @Inject
    SearchPresenter presenter;

    private Unbinder unbinder;
    private SearchAdapter adapter;

    @Inject
    SearchController() {
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.search_controller, container, false);
        ((VyngApp) getActivity().getApplication()).getAppComponent().inject(this);

        unbinder = ButterKnife.bind(this, view);
        presenter.setView(this);
        setUpRecyclerView();
        return view;
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
        presenter.unsubscribe();
    }

    @OnClick(R.id.search)
    public void submit(View view) {
        presenter.loadVideos(search.getText().toString());
    }

    @Override
    public void showVideos(List<DataItem> gifs) {
        adapter.setData(gifs);
    }

    @Override
    public void showErrorMessage() {

    }

    private void setUpRecyclerView() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        rec.setLayoutManager(manager);

        adapter = new SearchAdapter();
        rec.setAdapter(adapter);
    }
}
