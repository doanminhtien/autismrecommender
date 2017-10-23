package com.doanminhtien.chandoantuky;

/**
 * Created by doanminhtien on 03/10/2017.
 */

public class Child {
    private String mName;
    private String mDateOfBirth;
    private String mFatherName;
    private String mMotherName;
    private int mChildID;

    public int getChildID() {
        return mChildID;
    }

    public void setChildID(int mChildID) {
        this.mChildID = mChildID;
    }

    private ExtraInfo mExtraInfo;


    public Child(String name, String dateOfBirth, String fatherName, String motherName)
    {
        mName = name;
        mDateOfBirth = dateOfBirth;
        mFatherName = fatherName;
        mMotherName = motherName;
    }

    public String getShortInfo()
    {
        return    "Name: " + mName +"\n"
                + "Date of birth: " + mDateOfBirth + "\n"
                + "Father: " + mFatherName + "\n"
                + "Mother: " + mMotherName;
    }

    public ExtraInfo getExtraInfo() {
        return mExtraInfo;
    }

    public void setExtraInfo(ExtraInfo mExtraInfo) {
        this.mExtraInfo = mExtraInfo;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDateOfBirth() {
        return mDateOfBirth;
    }

    public void setDateOfBirth(String mDateOfBirth) {
        this.mDateOfBirth = mDateOfBirth;
    }


    public String getFatherName() {
        return mFatherName;
    }

    public void setFatherName(String mFatherName) {
        this.mFatherName = mFatherName;
    }

    public String getMotherName() {
        return mMotherName;
    }

    public void setMotherName(String mMotherName) {
        this.mMotherName = mMotherName;
    }
}
