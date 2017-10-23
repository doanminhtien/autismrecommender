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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by doanminhtien on 02/10/2017.
 */

public class FragmentChildren extends FragmentPage {
    public static final String TAG = "FragmentChildren";
    RecyclerView mExamRecyclerView;
    LinearLayoutManager mExamLayoutManager;
    ExamAdapter mExamAdapter;
    TextView mChildNameTextView;
    TextView mDateOfBirthTextView;
    TextView mFatherNameTextView;
    TextView mMotherNameTextView;
    List<Examination> mListOfExamination;
    public static FragmentChildren newInstance()
    {
        return new FragmentChildren();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_children, container, false);
        final ImageView mTitleNameOffImageView = (ImageView) view.findViewById(R.id.child_name_off);
        final LinearLayout mBasicInfoContainer = (LinearLayout) view.findViewById(R.id.basic_infor_container);
        mChildNameTextView = (TextView) view.findViewById(R.id.child_name);

        mDateOfBirthTextView = (TextView) view.findViewById(R.id.date_of_birth);
        mFatherNameTextView = (TextView) view.findViewById(R.id.father_name);
        mMotherNameTextView = (TextView) view.findViewById(R.id.mother_name);
        mChildNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBasicInfoContainer.getVisibility()==View.GONE)
                {
                    mBasicInfoContainer.setVisibility(View.VISIBLE);
                    mTitleNameOffImageView.setBackgroundResource(R.drawable.ic_action_keyboard_arrow_down);
                }else
                {
                    mBasicInfoContainer.setVisibility(View.GONE);
                    mTitleNameOffImageView.setBackgroundResource(R.drawable.ic_action_keyboard_arrow_right);

                }
            }
        });
        mExamRecyclerView = (RecyclerView) view.findViewById(R.id.examination_recyclerview);
        mExamLayoutManager = new LinearLayoutManager(this.getActivity());
        mExamRecyclerView.setLayoutManager(mExamLayoutManager);

        (new FetchExaminationListTask()).execute();
        //updateUI();
        return view;
    }


    private class FetchExaminationListTask extends AsyncTask<Void, Void, List<Examination>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Examination> examinations) {
            super.onPostExecute(examinations);
            if(mListOfExamination!=null) mListOfExamination.clear();
            mListOfExamination = examinations;
            updateUI();

        }

        @Override
        protected List<Examination> doInBackground(Void... voids) {
            return JsonDownloader.parseListOfExamination(TestUrls.getExaminationList);
        }
    }


    public void updateUI()
    {
        if(mListOfExamination==null)
        {
            mListOfExamination = new ArrayList<>();

        }

//        exams.add(new Examination("Lan dau tien", Boolean.TRUE, 3, "2017-01-12" ));
//        exams.add(new Exam("Lan dau tien 2", Boolean.TRUE, 2, "2017-02-12" ));
//        exams.add(new Exam("Lan dau tien 3", Boolean.TRUE, 2, "2017-03-12" ));
//        exams.add(new Exam("Lan dau tien 4", Boolean.TRUE, 1, "2017-04-12" ));
//        exams.add(new Exam("Lan dau tien 4", Boolean.TRUE, 1, "2017-04-12" ));
//        exams.add(new Exam("Lan dau tien 4", Boolean.TRUE, 1, "2017-04-12" ));
//        exams.add(new Exam("Lan dau tien 4", Boolean.TRUE, 1, "2017-04-12" ));
//        exams.add(new Exam("Lan dau tien 4", Boolean.TRUE, 1, "2017-04-12" ));
//        exams.add(new Exam("Lan dau tien 4", Boolean.TRUE, 1, "2017-04-12" ));

        if(mExamAdapter==null)
        {
            Log.d(TAG, "updateUI, set adapter");
            mExamAdapter = new ExamAdapter(mListOfExamination);
            mExamRecyclerView.setAdapter(mExamAdapter);
        }else
        {
            //add the setSolutions in the adapter and call it here, after that you will need to call notify update...
            Log.d(TAG, "updateUI, adapter not null");
            mExamAdapter.setmExams(mListOfExamination);
            mExamRecyclerView.setAdapter(mExamAdapter);
            mExamAdapter.notifyDataSetChanged();
        }
    }




    private class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ExamHolder>
    {
        private List<Examination> mExams;

        public ExamAdapter(List<Examination> exams)
        {
            this.mExams = exams;
        }


        public void setmExams(List<Examination> exams)
        {
            this.mExams = exams;
        }


        @Override
        public ExamHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_exam, parent, false);
            return new ExamHolder(view);
        }

        @Override
        public void onBindViewHolder(ExamHolder holder, int position) {
            Examination exam = mExams.get(position);
            holder.bindComponent(exam);

        }

        @Override
        public int getItemCount() {
            return mExams.size();
        }


        public class ExamHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            private Examination mExam;
            public TextView mTextViewName;
            public TextView mExamInfo;

            //public TextView mTextViewContributer;

            public void bindComponent(Examination exam)
            {
                mExam = exam;
                mTextViewName.setText(mExam.getTitle());
                mExamInfo.setText(mExam.getExamInfo());
                //mTextViewContributer.setText(news.getmContributer());
            }

            public ExamHolder(View v)
            {
                super(v);
                v.setOnClickListener(ExamHolder.this);
                mTextViewName = (TextView) v.findViewById(R.id.exam_title);
                mExamInfo = (TextView) v.findViewById(R.id.exam_info);
                //mTextViewContributer = (TextView) v.findViewById(R.id.solution_recommend_contributer);
            }

            @Override
            public void onClick(View view) {
                Toast.makeText(FragmentChildren.this.getActivity(), "Click!!", Toast.LENGTH_SHORT).show();
            }
        }
    }


}


/*
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



public class FragmentChildren extends FragmentPage {
    public static final String TAG = "FragmentChildren";
    RecyclerView mChildrenRecyclerView;
    ChildrenAdapter mChildrenAdapter;
    LinearLayoutManager mChildrenLayoutManager;

    public static FragmentChildren newInstance()
    {
        return new FragmentChildren();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_children, container, false);
        mChildrenRecyclerView  = (RecyclerView) view.findViewById(R.id.children_recyclerview);
        mChildrenLayoutManager = new LinearLayoutManager(FragmentChildren.this.getActivity());
        mChildrenRecyclerView.setLayoutManager(mChildrenLayoutManager);
        updateUI();
        return view;
    }

    public void updateUI()
    {
        List<Child> children = new ArrayList<>();
        children.add(new Child("Doan Minh Tan", "2016-10-10", "Doan Minh Tien", "Unknown" ));
        children.add(new Child("Doan Minh Quang", "2016-10-10", "Doan Minh Tien", "Unknown" ));
        children.add(new Child("Doan Minh Nhat", "2016-10-10", "Doan Minh Tien", "Unknown" ));
        children.add(new Child("Doan Minh Diem Le", "2016-10-10", "Doan Minh Tien", "Unknown" ));

        if(mChildrenAdapter==null)
        {
            Log.d(TAG, "updateUI, set adapter");
            mChildrenAdapter = new ChildrenAdapter(children);
            mChildrenRecyclerView.setAdapter(mChildrenAdapter);
        }else
        {
            //add the setSolutions in the adapter and call it here, after that you will need to call notify update...
            Log.d(TAG, "updateUI, adapter not null");
            mChildrenAdapter.setNewss(children);
            mChildrenRecyclerView.setAdapter(mChildrenAdapter);
            mChildrenAdapter.notifyDataSetChanged();
        }
    }



    private class ChildrenAdapter extends RecyclerView.Adapter<ChildrenAdapter.ChildHolder>
    {
        private List<Child> mChildren;

        public ChildrenAdapter(List<Child> children)
        {
            this.mChildren = children;
        }


        public void setNewss(List<Child> children)
        {
            this.mChildren = children;
        }


        @Override
        public ChildHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_child, parent, false);
            return new ChildHolder(view);
        }

        @Override
        public void onBindViewHolder(ChildHolder holder, int position) {
            Child child = mChildren.get(position);
            holder.bindComponent(child);

        }

        @Override
        public int getItemCount() {
            return mChildren.size();
        }


        public class ChildHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            private Child mChild;
            public TextView mTextViewName;
            public TextView mShortInfo;

            //public TextView mTextViewContributer;

            public void bindComponent(Child child)
            {
                mChild = child;
                mTextViewName.setText(mChild.getmName());
                mShortInfo.setText(mChild.getShortInfo());
                //mTextViewContributer.setText(news.getmContributer());
            }

            public ChildHolder(View v)
            {
                super(v);
                v.setOnClickListener(ChildHolder.this);
                mTextViewName = (TextView) v.findViewById(R.id.child_name);
                mShortInfo = (TextView) v.findViewById(R.id.child_short_infor);
                //mTextViewContributer = (TextView) v.findViewById(R.id.solution_recommend_contributer);
            }

            @Override
            public void onClick(View view) {
                Toast.makeText(FragmentChildren.this.getActivity(), "Click!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
*/