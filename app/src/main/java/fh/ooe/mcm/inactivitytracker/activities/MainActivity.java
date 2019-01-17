package fh.ooe.mcm.inactivitytracker.activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import fh.ooe.mcm.inactivitytracker.R;
import fh.ooe.mcm.inactivitytracker.interfaces.Observable;
import fh.ooe.mcm.inactivitytracker.interfaces.Observer;
import fh.ooe.mcm.inactivitytracker.utils.DatabaseHandler;
import fh.ooe.mcm.inactivitytracker.utils.LockScreenReceiver;
import fh.ooe.mcm.inactivitytracker.utils.Recognizer;
import fh.ooe.mcm.inactivitytracker.utils.TextToSpeechManager;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, Observable { //}, Observer {
    //TextToSpeech tts;

    ArrayList<Observer> observers;
    TextView numberOfRecords;
    DatabaseHandler databaseHandler;

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
//        recognizer.addObserver(this);


        SwitchCompat measurementToggle = findViewById(R.id.measurementToggle);
        if(measurementToggle != null) {
            measurementToggle.setOnCheckedChangeListener(this);
        }
        notifyAll(measurementToggle.isChecked());
        //databaseHandler.getAllActivitiesForDays(0, 0);
//        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
//
//            @Override
//            public void onInit(int status) {
//                if(status == TextToSpeech.SUCCESS){
//                    int result=tts.setLanguage(Locale.US);
//                    if(result == TextToSpeech.LANG_MISSING_DATA ||
//                            result==TextToSpeech.LANG_NOT_SUPPORTED){
//                        Log.e("error", "This Language is not supported");
//                    }
//                    else{
//                        convertTextToSpeech("");
//                    }
//                }
//                else
//                    Log.e("error", "Initilization Failed!");
//            }
//        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
       notifyAll(isChecked);
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


//    public void convertTextToSpeech(String text) {
//        if(text == null || "".equals(text))
//        {
//            text = "Content not available";
//        }
//
//        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
//    }

//    public void update(Observable observable, Object object) {
//        if(observable instanceof Recognizer) {
//            if(object instanceof String) {
//                final String text = (String) object;
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        convertTextToSpeech(text);
//                    }
//                });
//            }
//        }
//    }
}
