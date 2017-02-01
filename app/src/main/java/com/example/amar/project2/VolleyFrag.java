package com.example.amar.project2;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class VolleyFrag extends Fragment {

    private TextView VOLLtemperature, VOLLpressure, VOLLhumidity;

    public VolleyFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_volley, container, false);
        VOLLtemperature = (TextView) view.findViewById(R.id.vollTemp);
        VOLLpressure = (TextView) view.findViewById(R.id.VOLLpressure);
        VOLLhumidity = (TextView) view.findViewById(R.id.VOLLhumidity);
        volleyRequest();
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
            VOLLtemperature.setText("FAIL");
            VOLLpressure.setText("FAIL");
            VOLLhumidity.setText("FAIL");

        }
        VOLLtemperature.setText("" + temperature);
        VOLLpressure.setText("" + pressure);
        VOLLhumidity.setText("" + humidity);

    }


    private void volleyRequest() {
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String key = " here you place your API key2 ";
        String url = "http://api.openweathermap.org/data/2.5/weather?id=2692969&units=metric&appid=4e868b7574c8bbc38d548d30ab186370";
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        updateText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", error.getMessage(), error);
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
        //hej
    }
}
