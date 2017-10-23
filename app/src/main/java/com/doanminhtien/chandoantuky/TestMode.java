package com.doanminhtien.chandoantuky;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by doanminhtien on 05/10/2017.
 */

public class TestMode {
    private boolean on = true;

    public TestMode(boolean on)
    {
        setOn(on);
    }

    public void ShowMessage(Context context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }
}
