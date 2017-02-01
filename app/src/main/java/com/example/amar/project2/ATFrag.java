package com.example.amar.project2;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class ATFrag extends Fragment {

    private TextView ATtemp, ATpressure, AThumidity;

    public ATFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_at, container, false);
        ATtemp = (TextView) view.findViewById(R.id.ATtemp);
        ATpressure = (TextView) view.findViewById(R.id.ATpressure);
        AThumidity = (TextView) view.findViewById(R.id.AThumidity);
        new GetWeather().execute();
        return view;
    }

    public void updateText(String response) {
        String temperature = "";
        String pressure = "";
        String humidity = "";
        String temp_min;
        String temp_maax;
        if (response != null) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonObject1 = jsonObject.getJSONObject("main");

                temperature = jsonObject1.getString("temp");
                pressure = jsonObject1.getString("pressure");
                humidity = jsonObject1.getString("humidity");


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            ATtemp.setText("FAIL");
            ATpressure.setText("FAIL");
            AThumidity.setText("FAIL");

        }
        ATtemp.setText("" + temperature);
        ATpressure.setText("" + pressure);
        AThumidity.setText("" + humidity);

    }
    private class GetWeather extends AsyncTask<Void, Void, String> {


        private ProgressDialog pd;
        private String jsonWeather;

        @Override
        protected void onPostExecute(String response) {
            if (response == null) {
                response = "THERE  WAS AN ERROR";
            }
            updateText(response);
//            pd.cancel();
        }

//        protected void onPreExecute() {
//            pd = new ProgressDialog(getContext());
//            pd.setMessage("Loading weather");
//            pd.show();
//        }

        @Override
        protected String doInBackground(Void... voids) {
//            String mKey = "4e868b7574c8bbc38d548d30ab186370\n";
            String mUrl = "http://api.openweathermap.org/data/2.5/weather?id=2692969&units=metric&appid=4e868b7574c8bbc38d548d30ab186370";

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
    }
}
