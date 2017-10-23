package com.doanminhtien.chandoantuky;

/**
 * Created by doanminhtien on 20/10/2017.
 */
//import org.apache.http.HttpClientConnection;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.entity.ContentType;
//import org.apache.http.entity.mime.MultipartEntityBuilder;
//import org.apache.http.entity.mime.HttpMultipartMode;
//import org.apache.http.entity.mime.content.StringBody;
//import org.apache.http.impl.DefaultHttpClientConnection;
//import org.apache.http.util.EntityUtils;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cz.msebera.android.httpclient.HttpClientConnection;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.ContentType;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.StringBody;
import cz.msebera.android.httpclient.impl.DefaultHttpClientConnection;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class AddChildPost {

    public static final String TAG = "AddChildPost";

    public static void upload(String urlSpec, File file, JSONObject jsonObject) throws MalformedURLException, IOException, JSONException
    {
        Log.d(TAG, urlSpec);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
        multipartEntity.addBinaryBody("file", file, ContentType.create("image/jpeg"), file.getName());
        multipartEntity.addPart("token", new StringBody(jsonObject.getString("token"), ContentType.TEXT_PLAIN));
        multipartEntity.addPart("fullname", new StringBody(jsonObject.getString("fullname"), ContentType.TEXT_PLAIN));
        multipartEntity.addPart("date_of_birth", new StringBody(jsonObject.getString("date_of_birth"), ContentType.TEXT_PLAIN));
        multipartEntity.addPart("father", new StringBody(jsonObject.getString("father"), ContentType.TEXT_PLAIN));
        multipartEntity.addPart("mother", new StringBody(jsonObject.getString("mother"), ContentType.TEXT_PLAIN));
        multipartEntity.addPart("father_career", new StringBody(jsonObject.getString("father_career"), ContentType.TEXT_PLAIN));
        multipartEntity.addPart("mother_career", new StringBody(jsonObject.getString("mother_career"), ContentType.TEXT_PLAIN));
        multipartEntity.addPart("monthly_income", new StringBody(jsonObject.getString("monthly_income"), ContentType.TEXT_PLAIN));
        multipartEntity.addPart("child_sex", new StringBody(jsonObject.getString("child_sex"), ContentType.TEXT_PLAIN));

        HttpClient client = new DefaultHttpClient();
        HttpPost put = new HttpPost(urlSpec);
        put.setEntity(multipartEntity.build());
        HttpResponse response = client.execute(put);
        int statusCode = response.getStatusLine().getStatusCode();
        Log.d(TAG, "Status code: " + statusCode);

    }

}
