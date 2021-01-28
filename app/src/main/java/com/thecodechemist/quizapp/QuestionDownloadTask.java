package com.thecodechemist.quizapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class QuestionDownloadTask extends AsyncTask<String, String, String> {

    ArrayList<Question> mQuestions;
    CallBackListener mListener;

    public QuestionDownloadTask(ArrayList<Question> questions) {
        mQuestions = questions;

    }

    public void setListener(CallBackListener listener) {
        mListener = listener;
    }

    @Override
    protected String doInBackground(String... urls) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            //Attempt to make the connection using the passed String url
            URL url = new URL(urls[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
                Log.e("Response: ", "> " + line);
            }
            return buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject fullJson = new JSONObject(s);
            JSONArray jsonResults = fullJson.getJSONArray("results");
            for (int i = 0; i < jsonResults.length(); i++) {
                JSONObject jsonFullQuestionObject = jsonResults.getJSONObject(i);
                String question = jsonFullQuestionObject.getString("question");
                String correctAnswer = jsonFullQuestionObject.getString("correct_answer");
                String type = jsonFullQuestionObject.getString("type");
                JSONArray jsonIncorrectAnswers = jsonFullQuestionObject.getJSONArray("incorrect_answers");
                String incorrectAnswer1 = jsonIncorrectAnswers.getString(0);
                if(type.equals("multiple")) {
                    String incorrectAnswer2 = jsonIncorrectAnswers.getString(1);
                    String incorrectAnswer3 = jsonIncorrectAnswers.getString(2);
                    mQuestions.add(new Question(question, correctAnswer, incorrectAnswer1, incorrectAnswer2, incorrectAnswer3, type));
                } else {
                    mQuestions.add(new Question(question, correctAnswer, incorrectAnswer1, type));
                }
                

            }

            mListener.callback();

        Log.e("onPostExecute", "Complete");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
