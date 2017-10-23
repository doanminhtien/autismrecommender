package com.doanminhtien.chandoantuky;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by doanminhtien on 02/10/2017.
 */

public class FragmentPage extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public static FragmentPage newInstance(int page) {
        switch (page)
        {
            case 1:
                return FragmentDoTest.newInstance();
            case 2:
                return FragmentSolutions.newInstance();
            case 3:
                return FragmentChildren.newInstance();
        }
        return null;
    }
}
