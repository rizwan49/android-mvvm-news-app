package com.ar.newsapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ar.newsapp.R;
import com.ar.newsapp.Utils;
import com.ar.newsapp.network.model.NewsArticles;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an adapter which keeps the list of new's articles information;
 * 1. have n number of pages so based on pages getting new list of new's articles information.
 * 2. adding every time new list of information into existing list using addAllItem method.
 * 3. setting into viewHolder
 * 4. add click listener
 */

public class NewsHeadlinesAdapter extends RecyclerView.Adapter<NewsHeadlinesAdapter.MyViewHolder> {
    private final ListItemOnClickListener mOnClickListener;
    private List<NewsArticles> list;

    public NewsHeadlinesAdapter(List<NewsArticles> list, ListItemOnClickListener mOnClickListener) {
        this.list = list;
        this.mOnClickListener = mOnClickListener;
    }

    /***
     * this method used to add list into existing list
     * @param mList contains latest list which is fetched based on page wise;
     */
    public void addAllItem(final List<NewsArticles> mList) {
        if (list == null && mList != null && mList.size() > 0) {
            list = new ArrayList<>();
            list.addAll(mList);
            this.notifyDataSetChanged();
        }
    }

    /***
     * clear all the items of list;
     */
    public void clearAllItems() {
        if (list != null && list.size() > 0) {
            list.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.headline_item_list_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        if (list == null || list.size() == 0)
            return 0;
        return list.size();
    }

    /***
     * listener for viewHolder's items
     */
    public interface ListItemOnClickListener {
        void onListItemClick(NewsArticles selectedObject, View view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView headlineImage;
        TextView headlineTitle;

        MyViewHolder(View view) {
            super(view);
            init(view);
        }

        private void init(View view) {
            headlineTitle = view.findViewById(R.id.articlesHeadline);
            headlineImage = view.findViewById(R.id.articlesImage);
            itemView.setOnClickListener(this);
        }

        private void bindView(int position) {
            Utils.loadImage(itemView.getContext(), headlineImage, list.get(position).getUrlToImage(), R.drawable.ic_place_holder, R.drawable.ic_place_holder);
            headlineTitle.setText(list.get(position).getTitle());
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(list.get(getAdapterPosition()), v.findViewById(R.id.articlesImage));
        }
    }
}