package com.doanminhtien.chandoantuky;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by doanminhtien on 23/10/2017.
 */

public class InforManager {


    public static void clear(Context context)
    {
        saveUserEmail(null, context);
        saveUserFullname(null, context);
        saveChildId(-1, context);

    }

    public static void logout(Context context)
    {
        clear(context);
        saveToken(null, context);
    }



    public static void saveUserEmail(String email, Context context)
    {
        SharedPreferences sp = context.getSharedPreferences("InforManager", Context.MODE_PRIVATE);
        SharedPreferences.Editor Ed = sp.edit();
        Ed.putString("userEmail", email);
        Ed.apply();
    }

    public static String getUserEmail(Context context)
    {
        SharedPreferences sp1=context.getSharedPreferences("InforManager", Context.MODE_PRIVATE);

        String email=sp1.getString("userEmail", null);
        return email;
    }


    public static void saveUserFullname(String fullname, Context context)
    {
        SharedPreferences sp = context.getSharedPreferences("InforManager", Context.MODE_PRIVATE);
        SharedPreferences.Editor Ed = sp.edit();
        Ed.putString("fullname", fullname);
        Ed.apply();
    }

    public static String getUserFullname(Context context)
    {
        SharedPreferences sp1=context.getSharedPreferences("InforManager", Context.MODE_PRIVATE);

        String fullname=sp1.getString("fullname", null);
        return fullname;
    }


    public static void saveChildId(int childId, Context context)
    {
        SharedPreferences sp = context.getSharedPreferences("InforManager", Context.MODE_PRIVATE);
        SharedPreferences.Editor Ed = sp.edit();
        Ed.putInt("childId", childId);
        Ed.apply();
    }

    public static int getChildId(Context context)
    {
        SharedPreferences sp1=context.getSharedPreferences("InforManager", Context.MODE_PRIVATE);

        int id = sp1.getInt("childId", -1);
        return id;
    }





    public static void saveToken(String token, Context context) {
        SharedPreferences sp = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor Ed = sp.edit();
        Ed.putString("token", token);
        Ed.apply();
    }

    public static String getToken(Context context)
    {
        SharedPreferences sp1=context.getSharedPreferences("Login", Context.MODE_PRIVATE);

        String token=sp1.getString("token", null);
        return token;
    }
}
