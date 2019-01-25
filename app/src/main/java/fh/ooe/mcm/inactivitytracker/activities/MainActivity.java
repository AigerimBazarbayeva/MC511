package fh.ooe.mcm.inactivitytracker.activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;

import java.util.ArrayList;

import fh.ooe.mcm.inactivitytracker.R;
import fh.ooe.mcm.inactivitytracker.charts.ChartingActivity;
import fh.ooe.mcm.inactivitytracker.interfaces.Observable;
import fh.ooe.mcm.inactivitytracker.interfaces.Observer;
import fh.ooe.mcm.inactivitytracker.ui.CircleButton;
import fh.ooe.mcm.inactivitytracker.utils.DatabaseHandler;
import fh.ooe.mcm.inactivitytracker.utils.LockScreenReceiver;
import fh.ooe.mcm.inactivitytracker.utils.Recognizer;
import fh.ooe.mcm.inactivitytracker.utils.TextToSpeechManager;

public class MainActivity extends AppCompatActivity implements Observable {

    final String TRACKING_PREFERENCE = "TRACKING_PREF";
    final String TRACKING_KEY = "TRACKING";

    ArrayList<Observer> observers;
    DatabaseHandler databaseHandler;
    SharedPreferences trackingPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        observers = new ArrayList<>();

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);

        TextToSpeechManager textToSpeechManager = new TextToSpeechManager(this);

        databaseHandler = new DatabaseHandler(this.getBaseContext());

        IntentFilter lockFilter = new IntentFilter();
        lockFilter.addAction(Intent.ACTION_SCREEN_ON);
        lockFilter.addAction(Intent.ACTION_SCREEN_OFF);
        LockScreenReceiver lockScreenReceiver = new LockScreenReceiver();
        registerReceiver(lockScreenReceiver, lockFilter);

        Recognizer recognizer = new Recognizer(sensorManager,
                                                powerManager,
                                                getAssets(),
                                                textToSpeechManager,
                                                lockScreenReceiver,
                                                databaseHandler);

        addObserver(recognizer);

        CircleButton dataEntryButton = findViewById(R.id.dataEntryButton);
        CircleButton chartingButton = findViewById(R.id.chartingButton);
        CircleButton predictionButton = findViewById(R.id.predictionButton);
        SwitchCompat trackingSwitch = findViewById(R.id.trackingSwitch);


        if(dataEntryButton != null) {
            dataEntryButton.setOnClickListener(view -> {
                Intent dataEntryIntent = new Intent(this, DataEntryActivity.class);
                startActivity(dataEntryIntent);
            });
        }
        try {
            if (chartingButton != null) {
                chartingButton.setOnClickListener(view -> {
                    Intent chartingIntent = new Intent(this, ChartingActivity.class);
                    startActivity(chartingIntent);
                });
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        /*if(predictionButton != null) {
            predictionButton.setOnClickListener(view -> {
                Intent dataEntryIntent = new Intent(this, DataEntryActivity.class);
                startActivity(dataEntryIntent);
            });
        }*/

        if(trackingSwitch != null) {
            trackingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                notifyAll(isChecked);
                Editor editor = trackingPreferences.edit();
                editor.putBoolean(TRACKING_KEY, trackingSwitch.isChecked());
                editor.commit();
            });
        }

        trackingPreferences = getSharedPreferences(TRACKING_PREFERENCE, Context.MODE_PRIVATE);
        if(trackingPreferences.contains(TRACKING_KEY)) {
            trackingSwitch.setChecked(trackingPreferences.getBoolean(TRACKING_KEY, false));
        }
        notifyAll(trackingSwitch.isChecked());
    }

    @Override
    public void notifyAll(Object object) {
        for(Observer observer: observers) {
            observer.update(this, object);
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }
    
}
