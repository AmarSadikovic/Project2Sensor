package com.example.amar.project2;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Amar on 2017-01-31.
 */

public class RetrieveFeedTask {
    String mKey, mUrl;

    public RetrieveFinished delegate = null;

    protected void onPreExecute() {

    }

    protected void onPostExecute(String response) {
        if (response == null) {
            response = "THERE  WAS AN ERROR";
        }
        delegate.retrieveFinish(response); // hand over the response to the object
    }

    protected String doInBackground() {
        mKey = "4e868b7574c8bbc38d548d30ab186370\n";
        mUrl = "api.openweathermap.org/data/2.5/weather?q={city name}";

        try {
            URL url = new URL(mUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new
                        InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }


   public void retrieveFinish(String output) {
//        processJSON(output);
    }
}


