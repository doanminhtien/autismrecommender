package com.doanminhtien.chandoantuky;

/**
 * Created by doanminhtien on 09/10/2017.
 */

public class Exam {
    private int mExamId;
    private String mTitle;
    private Boolean mComplete;
    private int mResult;
    private String mCreatedDate;

    public Exam(String title, Boolean complete, int result, String createdDate)
    {
        mTitle = title;
        mComplete = complete;
        mResult = result;
        mCreatedDate = createdDate;
    }

    public String getExamInfo()
    {
        return "Exam Status: " + (mComplete?"Complete\n":"Not completed\n")+
                "Result: " + (mResult>0?"Positive":"Negative");
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Boolean isComplete() {
        return mComplete;
    }

    public void setComplete(Boolean mComplete) {
        this.mComplete = mComplete;
    }

    public int getResult() {
        return mResult;
    }

    public void setResult(int result) {
        this.mResult = result;
    }

    public String getmCreatedDate() {
        return mCreatedDate;
    }

    public void setmCreatedDate(String mCreatedDate) {
        this.mCreatedDate = mCreatedDate;
    }
}
