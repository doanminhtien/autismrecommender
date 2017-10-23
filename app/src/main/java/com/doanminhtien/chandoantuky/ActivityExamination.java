package com.doanminhtien.chandoantuky;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by doanminhtien on 20/10/2017.
 */

public class ActivityExamination extends AppCompatActivity {
    public static String EXTRA_EXAMINATION_ID = "com.doanminhtien.chandoantuky.ActivityExamination.EXTRA_EXAMINATION_ID";
    private String mExaminationId;


    public static Intent newIntent(Context context, String solutionId)
    {
        Intent i = new Intent(context, ActivitySolution.class);
        i.putExtra(EXTRA_EXAMINATION_ID, solutionId);
        return i;
    }

    private class FetchExaminationTask extends AsyncTask<Void, Void, Examination>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Examination examination) {
            super.onPostExecute(examination);
            List<Test> testList = examination.getTestList();
            for(int i=0; i< testList.size(); i++)
            {
                if(i==0)
                {
                    View v= newTestItem(testList.get(i), false);
                    mTestContainer.addView(v, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                }
                else
                {
                    View v= newTestItem(testList.get(i), !testList.get(i-1).isDone());
                    mTestContainer.addView(v, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                }

            }
        }

        @Override
        protected Examination doInBackground(Void... voids) {
            return JsonDownloader.parseSingleExamination(TestUrls.singleExamination);
        }
    }


    LinearLayout mTestContainer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination);

        Intent i = getIntent();

        mExaminationId = i.getStringExtra(EXTRA_EXAMINATION_ID);


        mTestContainer = (LinearLayout) findViewById(R.id.test_container);

        (new FetchExaminationTask()).execute();

//        Test test = new Test();
//        test.setTitle("Dau hieu co do");
//        test.setResult(1);
//        test.setDone(true);
//        View v= newTestItem(test, false);
//        mTestContainer.addView(v, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));





    }

    private View newTestItem(final Test test, boolean isDisable)
    {
        View v;
        LayoutInflater inflater = (LayoutInflater.from(this));
        v = inflater.inflate(R.layout.test_item, null);

        TextView title = (TextView) v.findViewById(R.id.test_title);
        TextView result = (TextView) v.findViewById(R.id.test_result);
        TextView status = (TextView) v.findViewById(R.id.test_status);
        ImageView statusImage = (ImageView) v.findViewById(R.id.imageview_status_text);

        title.setText(test.getTestTypeName());
        result.setText("Result: " + test.getResult());


        if(test.isDone())
        {
            status.setText("Done");
            statusImage.setBackgroundColor(Color.GREEN);
        }
        else
        {
            status.setText("Not done");
            statusImage.setBackgroundColor(Color.RED);
        }

        if(isDisable)
        {
            status.setText("Disabled");
            result.setText("Result: Unknown");
            statusImage.setBackgroundColor(Color.GRAY);
        }

        if(isDisable) return v;

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(test.isDone())
                {
                    //See result of the test
                }
                else
                {
                    //Do new (create new test)
                }
            }
        });

        return v;
    }
}
