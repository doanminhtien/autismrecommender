package com.doanminhtien.chandoantuky;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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
 * Created by doanminhtien on 18/10/2017.
 */

public class ActivityListChildren extends AppCompatActivity {
    private static final String TAG = "Activity List Children";

    RecyclerView mChildrenRecyclerView;
    LinearLayoutManager mChildrenLayoutManager;
    ChildAdapter mChildAdapter;

    FloatingActionButton addChildrenButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_children);
        addChildrenButton = (FloatingActionButton) findViewById(R.id.add_children_floating_button);
        addChildrenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityListChildren.this, ActivityAddChild.class);
                startActivity(intent);
            }
        });
        mChildrenRecyclerView = (RecyclerView) findViewById(R.id.children_list);
        mChildrenLayoutManager = new LinearLayoutManager(this);
        mChildrenRecyclerView.setLayoutManager(mChildrenLayoutManager);
        (new FetchChildrenTask()).execute();
    }


    private class FetchChildrenTask extends AsyncTask<Void, Void, List<Child>>
    {

        @Override
        protected List<Child> doInBackground(Void... voids) {
            return JsonDownloader.parseChildren(Domain.domain + Domain.getChildren + ActivityLogin.getToken(ActivityListChildren.this));
        }

        @Override
        protected void onPostExecute(List<Child> children) {
            super.onPostExecute(children);
            if(mChildAdapter==null)
            {
                mChildAdapter = new ChildAdapter(children);
                mChildrenRecyclerView.setAdapter(mChildAdapter);
            }
            else
            {
                mChildAdapter.setChilds(children);
                mChildrenRecyclerView.setAdapter(mChildAdapter);
                mChildAdapter.notifyDataSetChanged();
            }

        }
    }




    private class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ChildHolder>
    {
        private List<Child> mChilds;

        public ChildAdapter(List<Child> childs)
        {
            this.mChilds = childs;
        }


        public void setChilds(List<Child> childs)
        {
            this.mChilds = childs;
        }


        @Override
        public ChildHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(ActivityListChildren.this);
            View view = layoutInflater.inflate(R.layout.item_child, parent, false);
            return new ChildHolder(view);
        }

        @Override
        public void onBindViewHolder(ChildHolder holder, int position) {
            Child child = mChilds.get(position);
            holder.bindComponent(child);

        }

        @Override
        public int getItemCount() {
            return mChilds.size();
        }


        public class ChildHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            private Child mChild;
            public TextView mNameTextView;
            public TextView mShortInforTextView;

            //public TextView mTextViewContributer;

            public void bindComponent(Child child)
            {
                mChild = child;
                mNameTextView.setText(child.getName());
                mShortInforTextView.setText(child.getShortInfo());

                //mTextViewContributer.setText(child.getmContributer());
            }

            public ChildHolder(View v)
            {
                super(v);
                v.setOnClickListener(ChildHolder.this);
                mNameTextView = (TextView) v.findViewById(R.id.child_name);
                mShortInforTextView = (TextView) v.findViewById(R.id.child_short_infor);
                //mTextViewContributer = (TextView) v.findViewById(R.id.solution_recommend_contributer);
            }

            @Override
            public void onClick(View view) {
                InforManager.saveChildId(mChild.getChildID(), ActivityListChildren.this);
                Intent i = new Intent(ActivityListChildren.this, MainActivity.class);
                startActivity(i);
            }
        }
    }


}
