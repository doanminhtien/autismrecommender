package com.doanminhtien.chandoantuky;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by doanminhtien on 15/10/2017.
 */

public class TestRule {
    private List<Test> mTests;

    public TestRule()
    {
        mTests = new ArrayList<>();
    }

    public void addTest(Test test)
    {
        mTests.add(test);
    }
}
