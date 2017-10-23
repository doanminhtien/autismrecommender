package com.doanminhtien.chandoantuky;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by doanminhtien on 15/10/2017.
 */

public class Examination {
//    private List<Test> mDoneTestList;
//    private TestRule mTestRule;
    private List<Test> mTestList;
    private String mTitle;
    private boolean mCompleted;
    private String mCreatedDate;
    private int mId;
    private int mResult;
    //0 ~ no
    //1 ~ nhe
    //2 ~ trung binh
    //3 ~ nang

    public Examination()
    {
        mTitle = "";
        mCompleted = false;
        mCreatedDate = "";
        mId = -1;
        mResult = -1;
        mTestList = new ArrayList<>();
    }

    public String getExamInfo()
    {
        return "Exam Status: " + (mCompleted?"Complete\n":"Not completed\n")+
                "Result: " + (mResult>0?"Positive":"Negative");
    }

    public void addTest(Test test)
    {
        mTestList.add(test);
    }

    public List<Test> getTestList()
    {
        return mTestList;
    }


    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public void setCompleted(boolean completed) {
        mCompleted = completed;
    }

    public int getResult() {
        return mResult;
    }

    public void setResult(int result) {
        this.mResult = result;
    }

    public String getCreatedDate() {
        return mCreatedDate;
    }

    public void setCreatedDate(String mCreatedDate) {
        this.mCreatedDate = mCreatedDate;
    }
}
