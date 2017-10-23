package com.doanminhtien.chandoantuky;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by doanminhtien on 16/10/2017.
 */

public class ActivityDoTest extends AppCompatActivity {


    int examId;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        setContentView(R.layout.fragment_do_test);
    }
}
