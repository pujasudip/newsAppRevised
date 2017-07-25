package com.example.android.datafrominternet.utilities;


import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.datafrominternet.R;
import com.example.android.datafrominternet.models.ArticleField;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by pujasudip on 6/29/17.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.RecycleAdapterViewHolder> {
    private static final String TAG = Adapter.class.getSimpleName();
    private ArrayList<ArticleField> data;
    final private ItemClickListener listener;
    private Context mContext;

    public Adapter(ArrayList<ArticleField> data, ItemClickListener listener) {
        this.data = data;
        this.listener = listener;
    }
    public interface ItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
    @Override
    public RecycleAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.each_newsdisplay, viewGroup, shouldAttachToParentImmediately);

        return new RecycleAdapterViewHolder(view);
    }
    @Override
    public void onBindViewHolder(RecycleAdapterViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    class RecycleAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView newsTitle;
        TextView newsDescription;
        ImageView newsThumbnail;

        public RecycleAdapterViewHolder(View view) {
            super(view);
            newsThumbnail = (ImageView) view.findViewById(R.id.new_thumbnail);
            newsTitle = (TextView) view.findViewById(R.id.newsTitle);
            newsDescription = (TextView) view.findViewById(R.id.newsDescription);
            view.setOnClickListener(this);
        }

        public void bind(int pos) {
            mContext = newsThumbnail.getContext();
            ArticleField item = data.get(pos);
            Picasso.with(mContext).load(item.getUrlToImage()).into(newsThumbnail);
            newsTitle.setText(item.getTitle());
            newsDescription.setText(item.getDescription());
        }

        @Override
        public void onClick(View v) {
            listener.onListItemClick(getAdapterPosition());

        }
    }

    /*

    public void setData(ArrayList<ArticleField> data) {
        this.data = data;
    }
    */
}