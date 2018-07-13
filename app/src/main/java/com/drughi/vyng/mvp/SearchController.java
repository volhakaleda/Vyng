package com.drughi.vyng.mvp;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bluelinelabs.conductor.Controller;
import com.drughi.vyng.R;
import com.drughi.vyng.adapters.SearchAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SearchController extends Controller implements SearchContractor.View {

    @BindView(R.id.rec)
    RecyclerView rec;

    @BindView(R.id.input)
    EditText search;

    SearchPresenter presenter;

    private Unbinder unbinder;
    private SearchAdapter adapter;

    @Inject
    public SearchController() {
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.search_controller, container, false);

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


    private void setUpRecyclerView() {

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        rec.setLayoutManager(manager);

        adapter = new SearchAdapter();
        rec.setAdapter(adapter);

    }

    @Override
    public void showVideos() {

    }

    @Override
    public void showErrorMessage() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }
}
