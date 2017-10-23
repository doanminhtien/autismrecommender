package com.doanminhtien.chandoantuky;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by doanminhtien on 19/10/2017.
 */

public class ActivityTestingEverything extends AppCompatActivity{
    Button mButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_everything);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = ActivitySolution.newIntent(ActivityTestingEverything.this, "1");
//                startActivity(i);
//            }

            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityTestingEverything.this, ActivityListChildren.class);
                startActivity(i);
            }
        });

    }
}
