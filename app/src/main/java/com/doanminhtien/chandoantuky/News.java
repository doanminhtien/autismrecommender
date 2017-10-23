package com.doanminhtien.chandoantuky;

/**
 * Created by doanminhtien on 03/10/2017.
 */

public class News {
    private String mTitle;
    private String mDescription;
    private String mPicture;
    private String mLink;


    public News(String title, String description, String picture, String link)
    {
        mTitle = title;
        mDescription = description;
        mPicture = picture;
        mLink = link;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmPicture() {
        return mPicture;
    }

    public void setmPicture(String mPicture) {
        this.mPicture = mPicture;
    }

    public String getmLink() {
        return mLink;
    }

    public void setmLink(String mLink) {
        this.mLink = mLink;
    }
}
