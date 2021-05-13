package com.example.nymostpopular;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.example.nymostpopular.api.NYTApiClient;
import com.example.nymostpopular.api.NYTApiInterface;
import com.example.nymostpopular.pojo.MostViewedResponse;
import com.example.nymostpopular.pojo.Result;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nymostpopular.dummy.DummyContent;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An activity representing a list of Items.
 */
public class ItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private NYTApiInterface apiInterface;
    private List<Result> nytResultList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        getListOfMostViewed();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        recyclerView = (RecyclerView) findViewById(R.id.item_list);
        assert recyclerView != null;
    }

    /**
     * Get our list of most popular articles
     */
    public void getListOfMostViewed() {
        apiInterface = NYTApiClient.getClient().create(NYTApiInterface.class);
        Call<MostViewedResponse> call = apiInterface.getMostViewedList();

        call.enqueue(new Callback<MostViewedResponse>() {
            @Override
            public void onResponse(Call<MostViewedResponse> call, Response<MostViewedResponse> response) {

                MostViewedResponse nytResponse = response.body();

                if (nytResponse != null) {
                    nytResultList = nytResponse.results;
                    recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(nytResultList, mTwoPane));
                }
                else {
                    Log.d("tag", response.toString());
                }

            }

            @Override
            public void onFailure(Call<MostViewedResponse> call, Throwable t) {
                Log.d("tag", t.getMessage());
                call.cancel();
            }
        });
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Result> mArticles;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Result result = (Result) view.getTag();

                    Context context = view.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, new Gson().toJson(result));

                    context.startActivity(intent);
            }
        };

        SimpleItemRecyclerViewAdapter( List<Result> items, boolean twoPane) {
            mArticles = items;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mArticleTitle.setText(mArticles.get(position).title);
            holder.mArticleAuthor.setText(mArticles.get(position).byline);
            holder.mArticleDate.setText(mArticles.get(position).published_date);

            holder.itemView.setTag(mArticles.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mArticles.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mArticleTitle;
            final TextView mArticleAuthor;
            final TextView mArticleDate;

            ViewHolder(View view) {
                super(view);
                mArticleTitle = view.findViewById(R.id.article_title);
                mArticleAuthor = view.findViewById(R.id.article_author);
                mArticleDate = view.findViewById(R.id.article_date);
            }
        }
    }
}
