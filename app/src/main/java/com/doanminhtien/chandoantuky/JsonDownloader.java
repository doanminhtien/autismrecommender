package com.doanminhtien.chandoantuky;

import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import javax.net.ssl.HttpsURLConnection;

/**
 * Created by doanminhtien on 14/10/2017.
 */

public class JsonDownloader {
    private static final String TAG = "JsonDownloader";


    public static String uploadJson(String urlSpec, JSONObject jsonBody)
    {
        try
        {
            URL url = new URL(urlSpec);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonBody.toString());
//            writer.write(getPostDataString(jsonBody));

            writer.flush();
            writer.close();
            os.close();

            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in=new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line= "";

                while((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                String result =  sb.toString();
                JSONObject jsonResult = new JSONObject(result);
                String status = jsonResult.getString("status");
                if(status.equals("1"))
                {
                    return jsonResult.getString("token");
                }
                else
                {
                    return "";
                }

            }
            else {
                Log.d(TAG, "Response code: " + responseCode);
                return "";
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }


    public static String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        Log.d(TAG, result.toString());
        return result.toString();
    }

    public static byte[] getUrlBytes(String urlSpec, String METHOD) throws IOException
    {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(METHOD);
        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK)
            {
                throw new IOException(connection.getResponseMessage() + ": with" + urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while((bytesRead = in.read(buffer))>0)
            {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();

        }finally
        {
            connection.disconnect();
        }
    }


    public static String getUrlString(String urlSpec, String METHOD) throws IOException
    {
        return new String(getUrlBytes(urlSpec, METHOD));
    }

    public static Test parseTest(String url, String METHOD)
    {
        Test test = new Test();
        try
        {
            String jsonString = getUrlString(url, METHOD);
            JSONObject jsonBody = new JSONObject(jsonString);
            Iterator<String> panelKeys = jsonBody.keys();
            while(panelKeys.hasNext())
            {
                String title = panelKeys.next();
                JSONObject panel = jsonBody.getJSONObject(title);
                int testType = Integer.parseInt(panel.getString("test_type"));
                test.setTitle(title);
                test.setTestTypeId(testType);
                JSONArray jsonQuestionList = panel.getJSONArray("question_list");
                for(int i=0; i<jsonQuestionList.length(); i++)
                {
                    JSONObject jsonQuestion = jsonQuestionList.getJSONObject(i);
                    String content = jsonQuestion.getString("question");
                    Question question = new Question(content);
                    JSONArray jsonOptionList = jsonQuestion.getJSONArray("options");
                    for(int optIndex = 0; optIndex<jsonOptionList.length();optIndex++)
                    {
                        JSONObject jsonOption = jsonOptionList.getJSONObject(optIndex);
                        String option = jsonOption.getString("option");
                        question.addOption(option);
                    }
                    test.addQuestion(question);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return test;
    }


    public static List<Solution> parseSolutions(String url, String METHOD)
    {
        List<Solution> solutions = new ArrayList<>();
        Log.d(TAG, url);
        try
        {
            String jsonString = getUrlString(url, METHOD);
            Log.d(TAG,jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);


            String status = jsonBody.getString("status");
            //if(status=="0") return null;
            JSONArray solutionArray = jsonBody.getJSONArray("solutions");

            Log.d(TAG, solutionArray.toString());

            for(int i=0; i<solutionArray.length(); i++)
            {
                JSONObject solutionObject = solutionArray.getJSONObject(i);
                Log.d(TAG, solutionObject.toString());

                Iterator<String> panelKeys = solutionObject.keys();
                while(panelKeys.hasNext())
                {
                    JSONObject panel = solutionObject.getJSONObject(panelKeys.next());
                    String title = panel.getString("title");
                    String content = panel.getString("content");
                    String likes = panel.getString("likes");
                    Solution solution = new Solution();
                    solution.setTitle(title);
                    solution.setContent(content);
                    solution.setDescription(content);
                    solution.setmContributer("DOAN MINH TIEN");
                    solutions.add(solution);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return solutions;
    }


    public static String login(String token, String METHOD)
    {
        String url = Domain.domain + Domain.login + "&token=" + token;
        Log.d(TAG, url);
        try
        {
            String jsonString = getUrlString(url, METHOD);
            JSONObject jsonBody = new JSONObject(jsonString);
            String status = jsonBody.getString("status");
            if(status.equals("1"))
            {
                Log.d(TAG, token);
                return token;
            }
        }
        catch (Exception e)
        {
            return "";
        }
        return "";
    }

    public static String login(String username, String password, String METHOD)
    {
        String url = Domain.domain + Domain.login + "&username=" + username + "&password=" + password;
        Log.d(TAG, url);
        try
        {
            String jsonString = getUrlString(url, METHOD);
            Log.d(TAG, jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            String status = jsonBody.getString("status");
            if(status.equals("1"))
            {
                String token = jsonBody.getString("token");
                return token;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
        return "";
    }


    public static String register(JSONObject dataRegister)
    {

        String registerUrl = Domain.domain + Domain.register;
        Log.d(TAG, registerUrl);
        return uploadJson(registerUrl, dataRegister);
    }


    public static String register(String  url)
    {
        Log.d(TAG, url);
        try
        {
            String jsonString = getUrlString(url, "POST");
            Log.d(TAG, jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            String status = jsonBody.getString("status");
            if(status.equals("1"))
            {
                String token = jsonBody.getString("token");
                return token;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
        return "";

    }


    public static List<Child> parseChildren(String url)
    {
        List<Child> listChild = new ArrayList<>();
        Log.d(TAG, url);
        try
        {
            String jsonString = getUrlString(url, "GET");
            Log.d(TAG, jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            String status = jsonBody.getString("status");
            if(status.equals("1"))
            {
                JSONArray jsonListChildren = jsonBody.getJSONArray("children");
                for(int i=0; i<jsonListChildren.length(); i++)
                {
                    JSONObject jsonChild = jsonListChildren.getJSONObject(i);
                    Child child = new Child(jsonChild.getString("name"),
                            jsonChild.getString("date_of_birth"),
                            jsonChild.getString("father"),
                            jsonChild.getString("mother"));
                    child.setChildID(Integer.parseInt(jsonChild.getString("c_id")));
                    listChild.add(child);
                }
                return listChild;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return null;
    }


    public static List<CareerType> parseCareerType(String url)
    {
        List<CareerType> listCareerType = new ArrayList<>();
        Log.d(TAG, url);
        try
        {
            String jsonString = getUrlString(url, "GET");
            Log.d(TAG, jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);

            JSONArray jsonListCareerType = jsonBody.getJSONArray("career_type");
            for(int i=0; i<jsonListCareerType.length(); i++)
            {
                JSONObject jsonCareerType = jsonListCareerType.getJSONObject(i);
                CareerType careerType = new CareerType();
                careerType.setId(Integer.parseInt(jsonCareerType.getString("career_id")));
                careerType.setTitle(jsonCareerType.getString("career_title"));
                careerType.setDescription(jsonCareerType.getString("career_description"));
                listCareerType.add(careerType);
            }
            return listCareerType;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static List<IncomeType> parseIncomeType(String url)
    {
        List<IncomeType> incomeTypes = new ArrayList<>();
        Log.d(TAG, url);
        try
        {
            String jsonString = getUrlString(url, "GET");
            Log.d(TAG, jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);

            JSONArray jsonListIncomeType = jsonBody.getJSONArray("income_type_list");
            for(int i=0; i<jsonListIncomeType.length(); i++)
            {
                JSONObject jsonIncomeType = jsonListIncomeType.getJSONObject(i);
                IncomeType incomeType = new IncomeType();
                incomeType.setId(Integer.parseInt(jsonIncomeType.getString("income_id")));
                incomeType.setRange(jsonIncomeType.getString("range"));
                incomeTypes.add(incomeType);
            }
            return incomeTypes;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static Solution parseSingleSolution(String url)
    {
        Log.d(TAG, url);
        try
        {
            String jsonString = getUrlString(url, "GET");
            JSONObject jsonBody = new JSONObject(jsonString);
            Solution solution = new Solution();
            solution.setTitle(jsonBody.getString("title"));
            solution.setContent(jsonBody.getString("content"));
            solution.setLikes(Integer.parseInt(jsonBody.getString("likes")));
            solution.setSubs(Integer.parseInt(jsonBody.getString("subs")));
            solution.setOwnderEmail(jsonBody.getString("email"));
            solution.setmContributer(jsonBody.getString("contributer"));
            return solution;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    public static List<Examination> parseListOfExamination(String url)
    {
        Log.d(TAG, url);
        try
        {
            List<Examination> examinationList = new ArrayList<>();
            String jsonString = getUrlString(url, "GET");
            JSONObject jsonBody = new JSONObject(jsonString);
            JSONArray jsonExaminationList = jsonBody.getJSONArray("exam_list");
            for(int i=0; i<jsonExaminationList.length(); i++)
            {
                JSONObject jsonExamination = jsonExaminationList.getJSONObject(i);
                Examination examination = new Examination();
                examination.setId(Integer.parseInt(jsonExamination.getString("exam_id")));
                examination.setTitle(jsonExamination.getString("title"));
                examination.setResult(Integer.parseInt(jsonExamination.getString("exam_result")));
                examination.setCompleted(Integer.parseInt(jsonExamination.getString("status"))==1);
                examinationList.add(examination);
            }
            return examinationList;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return new ArrayList<Examination>();
        }
    }


    public static Examination parseSingleExamination(String url)
    {
        Log.d(TAG, url);
        try
        {
            String jsonString = getUrlString(url, "GET");
            JSONObject jsonBody = new JSONObject(jsonString);


            Examination examination = new Examination();
            examination.setTitle(jsonBody.getString("title"));
            examination.setId(Integer.parseInt(jsonBody.getString("exam_id")));
            examination.setResult(Integer.parseInt(jsonBody.getString("exam_result")));

            //Get the tests from the test rule and then pass the infor from test done later!!


            JSONArray jsonTestDoneList = jsonBody.getJSONArray("test_done");
            JSONObject jsonTestRule = jsonBody.getJSONObject("test_rule");
            JSONArray jsonTestRuleTestList = jsonTestRule.getJSONArray("levels");

            int numberOfDoneTests  = jsonTestDoneList.length();

            for(int i=0; i<jsonTestRuleTestList.length(); i++)
            {
                Test test = new Test();
                JSONObject jsonTest = jsonTestRuleTestList.getJSONObject(i);
                test.setTestTypeName(jsonTest.getString("type_name"));
                test.setTestTypeId(Integer.parseInt(jsonTest.getString("test_type_id")));
                test.setQuestionLink(jsonTest.getString("question_link"));
                //If the index of the test in testRule < (number of done tests) then it is done, otherwise it is not done
                if(i<numberOfDoneTests)
                {
                    test.setDone(true);
                    JSONObject jsonDoneTest = jsonTestDoneList.getJSONObject(i);
                    test.setResult(Integer.parseInt(jsonDoneTest.getString("result")));
                }else
                {
                    test.setDone(false);
                }
                examination.addTest(test);
            }

            return examination;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }




}
