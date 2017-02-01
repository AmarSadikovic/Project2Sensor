package com.example.amar.project2;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mTemperatureSensor;
    private Sensor mHumiditySensor;
    private Sensor mPressureSensor;
    private FragmentManager fm;
    private FrontFragment frontFragment;
    private ATFrag atFragment;
    private VolleyFrag volleyFrag;
    private boolean isTemperaturePresent = false, isPressurePresent = false, isHumiditySensorPresent = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getFragmentManager();
        frontFragment = new FrontFragment();
        atFragment = new ATFrag();
        volleyFrag = new VolleyFrag();
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);

//        mTemperatureValue = (TextView) findViewById(R.id.mTemperatureValue);
//        mPressureValue = (TextView) findViewById(R.id.mPressureValue);
//        mRelativeHumidityValue = (TextView) findViewById(R.id.mRelativeHumidityValue);
//        mAbsoluteHumidityValue = (TextView) findViewById(R.id.mAbsoluteHumidityValue);
//        mDewPointValue = (TextView) findViewById(R.id.mDewPointValue);

        initiateSensors();
        setFragment(volleyFrag, false);
        registerListener();

    }

    public void registerListener() {
        mSensorManager.registerListener(this, mPressureSensor, mSensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mHumiditySensor, mSensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mTemperatureSensor, mSensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregisterListener() {
        mSensorManager.unregisterListener(this);
        Toast.makeText(this, "Sensor unRegistered", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if (sensor.getType() == Sensor.TYPE_PRESSURE) {
            float pressure = event.values[0];
//            mPressureValue.setText("Preasure in mbar is" + pressure);
        } else if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            float temp = event.values[0];
//            mTemperatureValue.setText("Temperature: " + temp);
        } else if (sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
            float humidity = event.values[0];
//            mRelativeHumidityValue.setText("Humidity: " + humidity);
        }

//        mLocalPressureValue.setText("Local pressure from the service is: "+Float.toString(local_pressure));
//        float altitude = SensorManager.getAltitude(SensorManager.PRESSURE_STANDARD_ATMOSPHERE, pressure);
        Toast.makeText(this, "Sensor unRegistered", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



    public void setFragment(Fragment frag, boolean backstack) {
        FragmentTransaction fs = fm.beginTransaction();
        fs.replace(R.id.fl, frag);
        if (backstack) {
            fs.addToBackStack(null);
        }
        fs.commit();
        fm.executePendingTransactions();
    }


    public void initiateSensors(){
        //        Temperature SENSOR
        if (mSensorManager.getDefaultSensor
                (Sensor.TYPE_AMBIENT_TEMPERATURE) != null) {
            mTemperatureSensor = mSensorManager.getDefaultSensor
                    (Sensor.TYPE_AMBIENT_TEMPERATURE);
            isTemperaturePresent = true;
//            mTemperatureValue.setText("tEMP WORKS!");
        } else {
//            mTemperatureValue.setText("Ambient Temperature Sensor is not available!");
            isTemperaturePresent = false;
        }
//        Pressure SENSOR
        if (mSensorManager.getDefaultSensor(
                Sensor.TYPE_PRESSURE) != null) {
            mPressureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
            isPressurePresent = true;
        } else {
//            mPressureValue.setText("Pressure Sensor is not available!");
            isPressurePresent = false;
        }
//        Humidity SENSOR
        if (mSensorManager.getDefaultSensor
                (Sensor.TYPE_RELATIVE_HUMIDITY) != null) {
            mHumiditySensor = mSensorManager.getDefaultSensor
                    (Sensor.TYPE_RELATIVE_HUMIDITY);
            isHumiditySensorPresent = true;
        } else {
            Toast.makeText(this, "Relative Humidity Sensor is not available !", Toast.LENGTH_SHORT).show();
//            mRelativeHumidityValue.setText("Relative Humidity Sensor is not available !");
//            mAbsoluteHumidityValue.setText("Cannot calculate Absolute Humidity, as relative humidity sensor is"
//                    + " not available !");
//            mDewPointValue.setText("Cannot calculate Dew Point, as relative humidity sensor is not available" +
//                    "!");
            isHumiditySensorPresent = false;
        }
    }
}
