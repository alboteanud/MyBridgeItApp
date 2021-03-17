package com.craiovadata.mybridgeitapplication.ui.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.craiovadata.mybridgeitapplication.R;
import com.craiovadata.mybridgeitapplication.model.NewsItem;
import com.craiovadata.mybridgeitapplication.viewModel.NewsViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private SimpleItemRecyclerViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_news, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.newsitem_list);
        final View loadingIndicator = root.findViewById(R.id.news_loading_indicator);
        final View textEmptyNews = root.findViewById(R.id.text_empty_news);

        adapter = new SimpleItemRecyclerViewAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        NewsViewModel newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        newsViewModel.getNews().observe(getViewLifecycleOwner(), newsItems -> {
            loadingIndicator.setVisibility(View.GONE);
            updateRecyclerView(recyclerView, newsItems, textEmptyNews);
        });
        return root;
    }

    private void updateRecyclerView(@NonNull RecyclerView recyclerView, List<NewsItem> items, View textEmptyNews) {
        if (items == null) return;

        if (items.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            textEmptyNews.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            textEmptyNews.setVisibility(View.GONE);
        }

        adapter.mValues = items;
        adapter.notifyDataSetChanged();
    }

    private static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private List<NewsItem> mValues;
        private final View.OnClickListener mOnClickListener = view -> {
            NewsItem item = (NewsItem) view.getTag();

            Context context = view.getContext();
            Intent intent = new Intent(context, NewsWebViewActivity.class);
            intent.putExtra(NewsWebViewActivity.ARG_WEB_LINK, item.link);

            context.startActivity(intent);
        };

        SimpleItemRecyclerViewAdapter(List<NewsItem> items) {
            mValues = items;
        }

        @NotNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.newsitem_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.titleTextView.setText(mValues.get(position).title);
            holder.descriptionTextView.setText(mValues.get(position).description);

            String link = mValues.get(position).link;
            if (link == null || link.isEmpty()) return;
            SpannableString spannableLink = new SpannableString(link);
            spannableLink.setSpan(new UnderlineSpan(), 0, spannableLink.length(), 0);
            holder.linkTextView.setText(spannableLink);
            holder.linkTextView.setOnClickListener(mOnClickListener);
            holder.linkTextView.setTag(mValues.get(position));
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            final TextView titleTextView;
            final TextView descriptionTextView;
            final TextView linkTextView;

            ViewHolder(View view) {
                super(view);
                titleTextView = (TextView) view.findViewById(R.id.id_news_title);
                descriptionTextView = (TextView) view.findViewById(R.id.id_news_description);
                linkTextView = (TextView) view.findViewById(R.id.id_news_link);
            }
        }
    }


}