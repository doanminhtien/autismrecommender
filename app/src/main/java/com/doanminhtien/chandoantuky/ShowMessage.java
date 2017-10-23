package com.doanminhtien.chandoantuky;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by doanminhtien on 16/10/2017.
 */

public class ShowMessage {
    public static void show(Context context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
