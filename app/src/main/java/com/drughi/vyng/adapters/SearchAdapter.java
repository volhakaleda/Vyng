package com.drughi.vyng.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.drughi.vyng.R;
import com.drughi.vyng.data.model.DataItem;
import com.drughi.vyng.data.model.GifMutable;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Adapter used to display a grid of gifs pulled from {@link com.drughi.vyng.data.source.SearchRepository}
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private final List<GifMutable> list = new ArrayList<>();
    private final GifClickListener listener;

    @Inject
    public SearchAdapter(GifClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rowView = inflater.inflate(R.layout.view_row, parent, false);
        return new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        GifMutable gif = getItem(position);
        final String thumbnailUrl = gif.getUrl();
        Picasso.with(viewHolder.image.getContext()).load(thumbnailUrl).into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(final List<GifMutable> gifs) {
        this.list.clear();
        this.list.addAll(gifs);
        notifyDataSetChanged();
    }

    private GifMutable getItem(final int position) {
        return list.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        public void onGifClick(View view) {
            int position = getAdapterPosition();
            GifMutable gif = getItem(position);
            listener.onGifClick(gif.getMp4());
        }
    }

    public interface GifClickListener {
        void onGifClick(final String url);
    }
}

