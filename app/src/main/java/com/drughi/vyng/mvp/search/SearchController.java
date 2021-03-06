package com.drughi.vyng.mvp.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.drughi.vyng.R;
import com.drughi.vyng.VyngApp;
import com.drughi.vyng.adapters.SearchAdapter;
import com.drughi.vyng.data.model.GifMutable;
import com.drughi.vyng.mvp.play.PlayController;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * View to display a list of gifs based on user's input
 */
public class SearchController extends Controller implements SearchContract.View,
        SearchAdapter.GifClickListener{

    @BindView(R.id.rec)
    RecyclerView rec;

    @BindView(R.id.input)
    EditText search;

    @Inject
    SearchPresenter presenter;

    private Unbinder unbinder;
    private SearchAdapter adapter;

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
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        presenter.loadVideos(search.getText().toString());
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);
        presenter.unsubscribe();
    }

    @Override
    protected void onDestroyView(@NonNull View view) {
        super.onDestroyView(view);
        unbinder.unbind();
        unbinder = null;
    }

    @OnClick(R.id.search)
    public void submit() {
        presenter.loadVideos(search.getText().toString());
    }

    @Override
    public void showVideos(final List<GifMutable> gifs) {
        adapter.setData(gifs);
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(getActivity(), R.string.general_error_message, Toast.LENGTH_LONG).show();
    }

    //Things to improve: the number of columns is hardcoded in this example. Ideally it should be
    // defined dynamically based on device congifs
    private void setUpRecyclerView() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        rec.setLayoutManager(manager);
        adapter = new SearchAdapter(this);
        rec.setAdapter(adapter);
    }

    @Override
    public void onGifClick(final GifMutable gifMutable) {
        getRouter().pushController(RouterTransaction.with(new PlayController(gifMutable))
                .pushChangeHandler(new FadeChangeHandler())
                .popChangeHandler(new FadeChangeHandler()));
    }
}
