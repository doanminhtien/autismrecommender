package com.doanminhtien.chandoantuky;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

/**
 * Created by doanminhtien on 16/10/2017.
 */

public class ActivityRegister extends AppCompatActivity {

    private final static String TAG = "ActivityRegister";
    EditText mUserNameEditText,
            mPasswordEditText,
            mConfirmEditText,
            mFullnameEditText,
            mAddressEditText,
            mPhoneEditText,
            mEmailEditText,
            mDateOfBirthEditText;

    Button mRegisterButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mUserNameEditText = (EditText) findViewById(R.id.username);
        mPasswordEditText = (EditText) findViewById(R.id.password);
        mConfirmEditText = (EditText) findViewById(R.id.confirm_password);
        mFullnameEditText = (EditText) findViewById(R.id.full_name);
        mAddressEditText = (EditText) findViewById(R.id.address);
        mPhoneEditText = (EditText) findViewById(R.id.phone);
        mEmailEditText = (EditText) findViewById(R.id.email);
        mDateOfBirthEditText = (EditText) findViewById(R.id.date_of_birth);
        mRegisterButton = (Button) findViewById(R.id.register);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mPasswordEditText.getText().toString().equals(mConfirmEditText.getText().toString()))
                {
                    return;
                }
                try
                {
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("username", mUserNameEditText.getText().toString());
                    jsonBody.put("password", mPasswordEditText.getText().toString());
                    jsonBody.put("fullname", mFullnameEditText.getText().toString());
                    jsonBody.put("address", mAddressEditText.getText().toString());
                    jsonBody.put("phone", mPhoneEditText.getText().toString());
                    jsonBody.put("email", mEmailEditText.getText().toString());
                    jsonBody.put("age", mDateOfBirthEditText.getText().toString());
                    (new RegisterTask()).execute(jsonBody);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    ShowMessage.show(ActivityRegister.this, "Unknown failed reason, please check your connection and try again");
                }

            }
        });
    }


    private class RegisterTaskForm extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }

    private class RegisterTask extends AsyncTask<JSONObject, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mRegisterButton.setEnabled(false);
        }

        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            Log.d(TAG, jsonObjects[0].toString());
            return JsonDownloader.register(jsonObjects[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mRegisterButton.setEnabled(true);
            ShowMessage.show(ActivityRegister.this, s);
            Intent intent = new Intent(ActivityRegister.this, ActivityLogin.class);
            ActivityLogin.saveToken(s, ActivityRegister.this);
            startActivity(intent);
        }
    }

}
