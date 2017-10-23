package com.doanminhtien.chandoantuky;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by doanminhtien on 16/10/2017.
 */

public class ActivityLogin extends AppCompatActivity{
    private static final String TAG = "ACTIVITY LOGIN";
    EditText mUsernameEditText;
    EditText mPasswordEditText;
    Button mLoginButton;
    TextView mRegisterTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsernameEditText = (EditText) findViewById(R.id.username);
        mPasswordEditText = (EditText) findViewById(R.id.password);
        mLoginButton = (Button) findViewById(R.id.login);
        mRegisterTextView = (TextView) findViewById(R.id.register);

        mRegisterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLogin.this, ActivityRegister.class);
                startActivity(intent);
            }
        });

        String token = getToken(ActivityLogin.this);



        Log.d(TAG, "Token: " + token);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsernameEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                //password = md5(password);
                (new LoginNormal()).execute(username, password);

            }
        });

        if(token!=null && !token.equals(""))
        {
            (new LoginTokenTask()).execute(token);
        }
    }

    class LoginNormal extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoginButton.setEnabled(false);
        }

        @Override
        protected String doInBackground(String... strings) {
            return JsonDownloader.login(strings[0], strings[1], "POST");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals(""))
            {
                mLoginButton.setEnabled(true);
                saveToken("", ActivityLogin.this);
                ShowMessage.show(ActivityLogin.this, "Login failed!");
            }else
            {
                saveToken(s, ActivityLogin.this);
                Intent intent = new Intent(ActivityLogin.this, ActivityListChildren.class);
                startActivity(intent);
                finish();
            }
        }
    }


    class LoginTokenTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoginButton.setEnabled(false);
        }

        @Override
        protected String doInBackground(String... strings) {

            return JsonDownloader.login(strings[0], "GET");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mLoginButton.setEnabled(true);
            if(s.equals(""))
            {
                ActivityLogin.saveToken("", ActivityLogin.this);
                finish();
                startActivity(getIntent());
            }else
            {
                Intent intent = new Intent(ActivityLogin.this, ActivityListChildren.class);
                startActivity(intent);
                finish();
            }
        }
    }




    public static void saveToken(String token, Context context)
    {
        SharedPreferences sp= context.getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor Ed=sp.edit();
        Ed.putString("token",token );
        Ed.apply();
    }

    public static String getToken(Context context)
    {
        SharedPreferences sp1=context.getSharedPreferences("Login", MODE_PRIVATE);

        String token=sp1.getString("token", null);
        return token;
    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
