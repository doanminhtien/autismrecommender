package com.doanminhtien.chandoantuky;

import android.os.AsyncTask;
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

import junit.framework.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by doanminhtien on 02/10/2017.
 */

public class FragmentSolutions extends FragmentPage {

    private static final String TAG = "FragmentSolutions";

    RecyclerView recommendRecyclerView;
    LinearLayoutManager recommendRecyclerViewLayoutManager;
    RecommendAdapter mRecommendAdapter;
    RecyclerView topSolutionRecyclerView;
    LinearLayoutManager topSolutionRecyclerViewLayoutManger;
    TopSolutionAdapter mTopSolutionAdapter;

    List<Solution> mTopSolution;
    List<Solution> mRecommendSolution;

    public static FragmentSolutions newInstance()
    {
        FragmentSolutions fragmentSolutions = new FragmentSolutions();
        return fragmentSolutions;
    }


    public void updateUI()
    {
        List<Solution> solutions = new ArrayList<Solution>();
        solutions.add(new Solution("Title", "Description one i was seven year olds, I and my friend fly to the sky", "Nguyen Thi Minh Khai" ,"http://linkl.com"));
        solutions.add(new Solution("Title1", "Description one i was seven year olds, I and my friend fly to the sky","Hai Tran Minh Nhi" ,"http://linkl1.com"));
        solutions.add(new Solution("Title2", "Description one i was seven year olds, I and my friend fly to the sky","Le Hai Tran Bao" ,"http://linkl2.com"));
        solutions.add(new Solution("Title3", "Description one i was seven year olds, I and my friend fly to the sky", "Le Thien Thong Nhat" ,"http://linkl3.com"));
        if(mRecommendAdapter==null)
        {
            Log.d(TAG, "updateUI, set adapter");
            mRecommendAdapter = new RecommendAdapter(solutions);
            recommendRecyclerView.setAdapter(mRecommendAdapter);
        }else
        {
            //add the setSolutions in the adapter and call it here, after that you will need to call notify update...
        }

        if(mTopSolutionAdapter==null)
        {
            mTopSolutionAdapter = new TopSolutionAdapter(solutions);
            topSolutionRecyclerView.setAdapter(mTopSolutionAdapter);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_solutions, container, false);
        recommendRecyclerView = (RecyclerView) view.findViewById(R.id.recommended_recyclerview);
        recommendRecyclerViewLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recommendRecyclerView.setLayoutManager(recommendRecyclerViewLayoutManager);
        topSolutionRecyclerView = (RecyclerView) view.findViewById(R.id.top_solutions_recyclerview);
        topSolutionRecyclerViewLayoutManger = new LinearLayoutManager(this.getActivity());
        topSolutionRecyclerView.setLayoutManager(topSolutionRecyclerViewLayoutManger);
        updateUI();
        (new FetchTopSolutionTask()).execute();
        return view;
    }


    private class FetchTopSolutionTask extends AsyncTask<Void, Void, List<Solution>>
    {

        @Override
        protected List<Solution> doInBackground(Void... voids) {
            Log.d(TAG, "do in background");
            return JsonDownloader.parseSolutions(Domain.domain + String.format(Domain.topSolution, InforManager.getToken(FragmentSolutions.this.getActivity()),1), "GET");
        }

        @Override
        protected void onPostExecute(List<Solution> solutions) {
            Log.d(TAG, "done do in background");
            mTopSolutionAdapter.setSolutions(solutions);
            mTopSolutionAdapter.notifyDataSetChanged();
        }
    }


    private class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommedHolder>
    {
        private List<Solution> mSolutions;

        public RecommendAdapter(List<Solution> solutions)
        {
            this.mSolutions = solutions;
        }



        @Override
        public RecommedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_solution_recommend, parent, false);
            return new RecommedHolder(view);
        }

        @Override
        public void onBindViewHolder(RecommedHolder holder, int position) {
            Solution solution = mSolutions.get(position);
            holder.bindComponent(solution);

        }

        @Override
        public int getItemCount() {
            return mSolutions.size();
        }


        public class RecommedHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            private Solution mSolution;
            public TextView mTextViewTitle;
            public TextView mTextViewDesc;
            public TextView mTextViewContributer;

            public void bindComponent(Solution solution)
            {
                mSolution = solution;
                mTextViewTitle.setText(solution.getmTitle());
                mTextViewDesc.setText(solution.getmDescription());
                mTextViewContributer.setText(solution.getmContributer());
            }

            public RecommedHolder(View v)
            {
                super(v);
                v.setOnClickListener(RecommedHolder.this);
                mTextViewTitle = (TextView) v.findViewById(R.id.solution_recommend_title);
                mTextViewDesc = (TextView) v.findViewById(R.id.solution_recommend_desc);
                mTextViewContributer = (TextView) v.findViewById(R.id.solution_recommend_contributer);
            }

            @Override
            public void onClick(View view) {
                Toast.makeText(FragmentSolutions.this.getActivity(), "Click!!", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private class TopSolutionAdapter extends RecyclerView.Adapter<TopSolutionAdapter.TopSolutionHodler>
    {
        private List<Solution> mSolutions;

        public TopSolutionAdapter(List<Solution> solutions)
        {
            this.mSolutions = solutions;
        }


        public void setSolutions(List<Solution> solutions)
        {
            mSolutions = solutions;
        }

        @Override
        public TopSolutionHodler onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_top_solutions, parent, false);
            return new TopSolutionHodler(view);
        }

        @Override
        public void onBindViewHolder(TopSolutionHodler holder, int position) {
            Solution solution = mSolutions.get(position);
            holder.bindComponent(solution);

        }

        @Override
        public int getItemCount() {
            return mSolutions.size();
        }


        public class TopSolutionHodler extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            private Solution mSolution;
            public TextView mTextViewTitle;
            public TextView mTextViewDesc;
            public TextView mTextViewContributer;

            public void bindComponent(Solution solution)
            {
                mSolution = solution;
                mTextViewTitle.setText(solution.getmTitle());
                mTextViewDesc.setText(solution.getmDescription());
                mTextViewContributer.setText(solution.getmContributer());
            }

            public TopSolutionHodler(View v)
            {
                super(v);
                v.setOnClickListener(TopSolutionHodler.this);
                mTextViewTitle = (TextView) v.findViewById(R.id.solution_recommend_title);
                mTextViewDesc = (TextView) v.findViewById(R.id.solution_recommend_desc);
                mTextViewContributer = (TextView) v.findViewById(R.id.solution_recommend_contributer);
            }

            @Override
            public void onClick(View view) {
                Toast.makeText(FragmentSolutions.this.getActivity(), "Click!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
