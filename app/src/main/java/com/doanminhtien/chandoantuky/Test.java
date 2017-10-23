package com.doanminhtien.chandoantuky;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by doanminhtien on 15/10/2017.
 */

public class Test {
    private static final String TAG = "TEST";
    private List<Question> mQuestions;
    private String mTitle;
    private int mTestTypeId;
    private String mTestTypeName;
    private int mResult;
    private boolean isDone;
    private String mQuestionLink;


    public String getQuestionLink() {
        return mQuestionLink;
    }

    public void setQuestionLink(String mQuestionLink) {
        this.mQuestionLink = mQuestionLink;
    }

    public String getTestTypeName() {
        return mTestTypeName;
    }

    public void setTestTypeName(String mTestTypeName) {
        this.mTestTypeName = mTestTypeName;
    }

    public int getResult() {
        return mResult;
    }

    public void setResult(int mResult) {
        this.mResult = mResult;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    private TestResult mTestResult;


    public interface TestResult
    {
        void onTestResult();
    }

    public Test(String title, int testTypeId)
    {
        mTitle = title;
        mTestTypeId = testTypeId;
        mQuestions = new ArrayList<>();
    }

    public Test()
    {
        mQuestions = new ArrayList<>();
    }

    public void downloadTest(String testUrl)
    {
        new TestFetcher().execute(testUrl);
    }



    public class TestFetcher extends AsyncTask<String, Void, Test>
    {

        @Override
        protected Test doInBackground(String... strings) {
            Log.d(TAG, strings[0]);
            return JsonDownloader.parseTest(strings[0], "GET");
        }

        @Override
        protected void onPostExecute(Test test) {
            Test.this.mTitle = test.getTitle();
            Test.this.mQuestions = test.getQuestions();
            Test.this.mTestTypeId = test.getTestTypeId();
            mTestResult.onTestResult();
        }
    }




    public void setOnTestResult(TestResult testResult)
    {
        mTestResult = testResult;
    }

    public int getTestTypeId() {
        return mTestTypeId;
    }

    public void setTestTypeId(int mTestTypeId) {
        this.mTestTypeId = mTestTypeId;
    }

    public TestResult getTestResult() {
        return mTestResult;
    }

    public void setTestResult(TestResult mTestResult) {
        this.mTestResult = mTestResult;
    }



    public void addQuestion(Question question)
    {
        mQuestions.add(question);
    }

    public List<Question> getQuestions() {
        return mQuestions;
    }

    public void setQuestions(List<Question> mQuestions) {
        this.mQuestions = mQuestions;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
