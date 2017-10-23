package com.doanminhtien.chandoantuky;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.net.URLEncoder;

/**
 * Created by doanminhtien on 19/10/2017.
 */

public class ActivitySolution extends AppCompatActivity {
    public static String EXTRA_SOLUTION_ID = "com.doanminhtien.chandoantuky.activitysolution.solution_id";
    public static final String TAG = "ActivitySolution";
    Button mSendEmailButton;
    String mSolutionId;

    TextView mMessageTextView;
    Solution currentSolution;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);

        Intent i = getIntent();
        mSolutionId = i.getStringExtra(EXTRA_SOLUTION_ID);

        mSendEmailButton = (Button) findViewById(R.id.send_email);
        mMessageTextView = (TextView) findViewById(R.id.message);
        mSendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto:doanminhtien@outlook.com","email@email.com", null));
//                i.setType("text/plain");
//                i.putExtra(Intent.EXTRA_TEXT, "This is the message" );
//                i.putExtra(Intent.EXTRA_SUBJECT, "About solution: " + currentSolution.getmTitle());
//                i = Intent.createChooser(i, "Select email via...");
                String bodyText = "Dear Mr/Mrs " + currentSolution.getmContributer() + ",\n" + "\n" +
                        mMessageTextView.getText() + "\n" + "\n" +
                        "Thank you so much,\n" +
                        "Doan Minh Tien"; //This should be the current user's name
                Log.d(TAG, bodyText);
                String uriText = "mailto:" + currentSolution.getOwnderEmail() +
                        "?subject=" + URLEncoder.encode("About solution: " + currentSolution.getmTitle()) +
                        "&body=" + bodyText;
                Uri uri = Uri.parse(uriText);
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(uri);
                startActivity(Intent.createChooser(sendIntent, "Send Email"));
                //startActivity(i);
            }
        });

        (new FetchSolutionTask()).execute();
    }


    public static Intent newIntent(Context context, String solutionId)
    {
        Intent i = new Intent(context, ActivitySolution.class);
        i.putExtra(EXTRA_SOLUTION_ID, solutionId);
        return i;
    }

    private class FetchSolutionTask extends AsyncTask<Void, Void, Solution>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mSendEmailButton.setEnabled(false);
        }

        @Override
        protected Solution doInBackground(Void... voids) {
            Log.d(TAG, "Solution id: " + mSolutionId);
            return JsonDownloader.parseSingleSolution(Domain.domain + "/singleSolution.txt");
        }

        @Override
        protected void onPostExecute(Solution solution) {
            super.onPostExecute(solution);
            if(solution==null)
            {
                ShowMessage.show(ActivitySolution.this, "Can not load the solution!");
                ActivitySolution.this.finish();
            }
            currentSolution = solution;
            mSendEmailButton.setEnabled(true);
        }
    }
}
