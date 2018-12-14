package fh.ooe.mcm.inactivitytracker.activities;

import android.content.Context;
import android.content.res.AssetManager;
import android.hardware.SensorManager;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import fh.ooe.mcm.inactivitytracker.R;
import fh.ooe.mcm.inactivitytracker.interfaces.Observable;
import fh.ooe.mcm.inactivitytracker.interfaces.Observer;
import fh.ooe.mcm.inactivitytracker.utils.Recognizer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);

        Recognizer recognizer = new Recognizer(sensorManager, powerManager, getAssets());
    }

}
