package com.doanminhtien.chandoantuky;

import java.util.ArrayList;

/**
 * Created by doanminhtien on 13/10/2017.
 */

public class Question {
    private String mContent;
    private ArrayList<String> mOptions;
    private float chosenOption;

    public int countOptions()
    {
        return mOptions.size();
    }

    public float getChosenOption() {
        return chosenOption;
    }

    public void setChosenOption(float chosenOption) {
        this.chosenOption = chosenOption;
    }

    public Question(String content)
    {
        this.mContent = content;
        mOptions = new ArrayList<>();
    }

    public void addOption(String option)
    {
        this.mOptions.add(option);
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public ArrayList<String> getOptions() {
        return mOptions;
    }

    public void setOptions(ArrayList<String> options) {
        this.mOptions = options;
    }
}
