package com.doanminhtien.chandoantuky;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CancellationException;

import static android.text.InputType.TYPE_NULL;

/**
 * Created by doanminhtien on 18/10/2017.
 */

public class ActivityAddChild extends AppCompatActivity{

    private static final String TAG = "ActivityAddChild";
    private static final int REQUEST_PHOTO = 2;

    EditText mFullnameEditText, mFatherEditText, mMotherEditText, mDateOfBirthTextView;
    Spinner mFatherCareerSpinner, mMotherCareerSpinner, mIncomeTypeSpinner, mSexSpinner;
    ArrayAdapter<String> mAdapterFather, mAdapterMother, mAdapterIncome, mAdapterSex;
    List<CareerType> listCareerType;
    List<IncomeType> listIncomeType;
    TextView mFatherCareerDescription, mMotherCareerDescription;
    ImageView mAvatarImageView;

    Button mRegisterButton;

    File mPhotoFile;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

        mFullnameEditText = (EditText) findViewById(R.id.fullname);
        mDateOfBirthTextView = (EditText) findViewById(R.id.date_of_birth);
        mDateOfBirthTextView.setInputType(TYPE_NULL);

//        mDateOfBirthTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDatePickerDialog(view);
//            }
//        });

        mDateOfBirthTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                showDatePickerDialog(view);
                return false;
            }
        });


        mFatherEditText = (EditText) findViewById(R.id.father_name);
        mMotherEditText = (EditText) findViewById(R.id.mother_name);

        mFatherCareerSpinner = (Spinner) findViewById(R.id.father_career);
        mFatherCareerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mFatherCareerDescription.setText(listCareerType.get(i).getDescription());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mMotherCareerSpinner = (Spinner) findViewById(R.id.mother_career);
        mMotherCareerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mMotherCareerDescription.setText(listCareerType.get(i).getDescription());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mIncomeTypeSpinner = (Spinner) findViewById(R.id.income);
        mSexSpinner = (Spinner) findViewById(R.id.sex);

        mAvatarImageView = (ImageView) findViewById(R.id.child_avatar);

        mFatherCareerDescription = (TextView) findViewById(R.id.father_career_description);
        mMotherCareerDescription = (TextView) findViewById(R.id.mother_career_description);

        mRegisterButton = (Button) findViewById(R.id.register);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject jsonChild = new JSONObject();
                if(validateFalse()) return;
                try
                {
                    jsonChild.put("fullname", mFullnameEditText.getText().toString());
                    jsonChild.put("date_of_birth", mDateOfBirthTextView.getText().toString());
                    jsonChild.put("father", mFatherEditText.getText().toString());
                    jsonChild.put("mother", mMotherEditText.getText().toString());
                    jsonChild.put("father_career", mFatherCareerSpinner.getSelectedItemPosition());
                    jsonChild.put("mother_career", mMotherCareerSpinner.getSelectedItemPosition());
                    jsonChild.put("monthly_income", mIncomeTypeSpinner.getSelectedItemPosition());
                    jsonChild.put("child_sex", mSexSpinner.getSelectedItemPosition());
                    jsonChild.put("token", InforManager.getToken(ActivityAddChild.this));
                    Log.d(TAG, jsonChild.toString());
                    AddChildPost.upload(Domain.domain+Domain.addChild, mPhotoFile, jsonChild);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    ShowMessage.show(ActivityAddChild.this, "Error!");
                }
            }
        });

        List<String> sexes = new ArrayList<>();
        sexes.add("Girl");
        sexes.add("Boy");
        mAdapterSex = new ArrayAdapter<String>(ActivityAddChild.this, android.R.layout.simple_spinner_item, sexes);
        mSexSpinner.setAdapter(mAdapterSex);



        File externalFilesDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        PackageManager packageManager = this.getPackageManager();

        mPhotoFile = new File(externalFilesDir, "IMG_" + UUID.randomUUID());
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile!=null && captureImage.resolveActivity(packageManager)!=null;
        mAvatarImageView.setEnabled(canTakePhoto);
        if(canTakePhoto)
        {

            Uri uri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".my.package.name.provider", mPhotoFile);
            captureImage.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        mAvatarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        (new FetchCareerTypeTask()).execute();
        (new FetchIncomeTypeTask()).execute();

    }

    private boolean validateFalse()
    {

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PHOTO)
        {
            updatePhotoView();
        }
    }

    private void updatePhotoView()
    {
        if(mPhotoFile ==null || !mPhotoFile.exists())
        {
            mAvatarImageView.setImageDrawable(null);
        } else
        {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), this
            );
            mAvatarImageView.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private class FetchIncomeTypeTask extends AsyncTask<Void, Void, List<IncomeType>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }



        @Override
        protected List<IncomeType> doInBackground(Void... voids) {
            return JsonDownloader.parseIncomeType(Domain.domain + Domain.getIncomeType);
        }

        @Override
        protected void onPostExecute(List<IncomeType> incomeTypes) {
            super.onPostExecute(incomeTypes);
            List<String> incomes = new ArrayList<>();
            for(int i=0; i<incomeTypes.size(); i++)
            {
                incomes.add(incomeTypes.get(i).getRange());
            }
            mAdapterIncome = new ArrayAdapter<String>(ActivityAddChild.this, android.R.layout.simple_spinner_item, incomes);
            mIncomeTypeSpinner.setAdapter(mAdapterIncome);
        }
    }

    private class FetchCareerTypeTask extends AsyncTask<Void, Void, List<CareerType>>
    {

        @Override
        protected List<CareerType> doInBackground(Void... voids) {
            return JsonDownloader.parseCareerType(Domain.domain + Domain.getCareerType);
        }

        @Override
        protected void onPostExecute(List<CareerType> careerTypes) {
            super.onPostExecute(careerTypes);
            listCareerType = careerTypes;
            List<String> careers = new ArrayList<>();
            for(int i=0; i<careerTypes.size();i++)
            {

                careers.add(careerTypes.get(i).getTitle());
                ///we are here
            }
            mAdapterFather = new ArrayAdapter<String>(ActivityAddChild.this, android.R.layout.simple_spinner_item, careers);
            mAdapterMother = new ArrayAdapter<String>(ActivityAddChild.this, android.R.layout.simple_spinner_item, careers);
            mFatherCareerSpinner.setAdapter(mAdapterFather);
            mMotherCareerSpinner.setAdapter(mAdapterMother);


        }
    }



    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        ((DatePickerFragment) newFragment).setEditText((EditText)v);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        private EditText mEditText;

        public void setEditText(EditText e)
        {
            mEditText = e;
        }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            mEditText.setText(month + "/" + day + "/" + year);
        }
    }

}
