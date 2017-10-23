package com.doanminhtien.chandoantuky;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by doanminhtien on 02/10/2017.
 */

public class FragmentNews extends FragmentPage {

    private final String TAG = "FragmentNews";

    RecyclerView mNewsRecycler;
    NewsAdapter mNewsAdapter;
    LinearLayoutManager mNewsLayoutManager;

    public static FragmentNews newInstance()
    {
        FragmentNews fragmentNews = new FragmentNews();
        return fragmentNews;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        mNewsRecycler = (RecyclerView) view.findViewById(R.id.news_recyclerview);
        mNewsLayoutManager = new LinearLayoutManager(this.getActivity());
        mNewsRecycler.setLayoutManager(mNewsLayoutManager);
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void setNews(List<News> newss)
    {
        mNewsAdapter.setNewss(newss);
        updateUI();
    }

    public void updateUI()
    {
        List<News> newss = new ArrayList<News>();
        newss.add(new News("Hành trình chữa tự kỷ của người mẹ 30 tuổi", "This is the description, you should not put many text here :). Hanhf trinh chua tu ky", "Nguyen Thi Minh Khai" ,"http://linkl.com"));
        newss.add(new News("Title1", "Description one i was seven year olds, I and my friend fly to the sky","Hai Tran Minh Nhi" ,"http://linkl1.com"));
        newss.add(new News("Title2", "Description one i was seven year olds, I and my friend fly to the sky","Le Hai Tran Bao" ,"http://linkl2.com"));
        newss.add(new News("Title3", "Description one i was seven year olds, I and my friend fly to the sky", "Le Thien Thong Nhat" ,"http://linkl3.com"));
        if(mNewsAdapter==null)
        {
            Log.d(TAG, "updateUI, set adapter");
            mNewsAdapter = new NewsAdapter(newss);
            mNewsRecycler.setAdapter(mNewsAdapter);
        }else
        {
            //add the setSolutions in the adapter and call it here, after that you will need to call notify update...
            Log.d(TAG, "updateUI, adapter not null");
            mNewsAdapter.setNewss(newss);
            mNewsRecycler.setAdapter(mNewsAdapter);
            mNewsAdapter.notifyDataSetChanged();
        }
    }


    private class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder>
    {
        private List<News> mNewss;

        public NewsAdapter(List<News> newss)
        {
            this.mNewss = newss;
        }


        public void setNewss(List<News> newss)
        {
            this.mNewss = newss;
        }


        @Override
        public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_news_2, parent, false);
            return new NewsHolder(view);
        }

        @Override
        public void onBindViewHolder(NewsHolder holder, int position) {
            News news = mNewss.get(position);
            holder.bindComponent(news);

        }

        @Override
        public int getItemCount() {
            return mNewss.size();
        }


        public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            private News mNews;
            public TextView mTextViewTitle;
            public TextView mTextViewDesc;
            //public TextView mTextViewContributer;

            public void bindComponent(News news)
            {
                mNews = news;
                mTextViewTitle.setText(news.getmTitle());
                mTextViewDesc.setText(news.getmDescription());
                //mTextViewContributer.setText(news.getmContributer());
            }

            public NewsHolder(View v)
            {
                super(v);
                v.setOnClickListener(NewsHolder.this);
                mTextViewTitle = (TextView) v.findViewById(R.id.item_news_title);
                mTextViewDesc = (TextView) v.findViewById(R.id.item_new_desc);
                //mTextViewContributer = (TextView) v.findViewById(R.id.solution_recommend_contributer);
            }

            @Override
            public void onClick(View view) {
                Toast.makeText(FragmentNews.this.getActivity(), "Click!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
