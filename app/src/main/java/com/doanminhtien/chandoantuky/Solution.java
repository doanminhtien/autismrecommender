package com.doanminhtien.chandoantuky;

/**
 * Created by doanminhtien on 03/10/2017.
 */

public class Solution {
    private String mTitle;
    private String mDescription;
    private String mContent;
    private String mContributer;
    private String mPicture;
    private int mSolutionId;
    private String mOwnderEmail;
    private int mLikes;
    private int mSubs;



    public int getLikes() {
        return mLikes;
    }

    public void setLikes(int mLikes) {
        this.mLikes = mLikes;
    }

    public int getSubs() {
        return mSubs;
    }

    public void setSubs(int mSubs) {
        this.mSubs = mSubs;
    }

    public String getOwnderEmail() {
        return mOwnderEmail;
    }

    public void setOwnderEmail(String mOwnderEmail) {
        this.mOwnderEmail = mOwnderEmail;
    }

    public int getSolutionId() {
        return mSolutionId;
    }

    public void setSolutionId(int mSolutionId) {
        this.mSolutionId = mSolutionId;
    }

    public String getmPicture() {
        return mPicture;
    }

    public void setmPicture(String mPicture) {
        this.mPicture = mPicture;
    }

    public String getmContributer() {
        return mContributer;
    }

    public void setmContributer(String mContributer) {
        this.mContributer = mContributer;
    }

    public Solution(String title, String desc, String content, String contributer)
    {
        mTitle = title;
        mDescription = desc;
        mContent = content;
        mContributer = contributer;
    }

    public Solution()
    {

    }

    public String getmTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }
}
