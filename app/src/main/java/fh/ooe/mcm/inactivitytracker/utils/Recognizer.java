package fh.ooe.mcm.inactivitytracker.utils;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.PowerManager;
import android.os.Process;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import fh.ooe.mcm.inactivitytracker.Activities;
import fh.ooe.mcm.inactivitytracker.Features;
import fh.ooe.mcm.inactivitytracker.activities.MainActivity;
import fh.ooe.mcm.inactivitytracker.interfaces.Observable;
import fh.ooe.mcm.inactivitytracker.interfaces.Observer;

public class Recognizer implements Observer, Observable {

    //final int WORKING_RATE = 2000; // two seconds

    ArrayList<Observer> observers;
    ArrayList<Integer> activities;
    ArrayList<Long> timestamps;

    Map<Long, String> filteredActivities;

    HandlerThread databaseThread;
    Handler databaseThreadHandler;

    boolean shouldMeasure;
    //long previousTime = 0;

    //DatabaseHandler databaseHandler;

    public Recognizer(SensorManager sensorManager,
                      PowerManager powerManager,
                      AssetManager assetManager,
                      TextToSpeechManager textToSpeechManager,
                      LockScreenReceiver lockScreenReceiver,
                      DatabaseHandler databaseHandler) {

        observers = new ArrayList<>();
        activities = new ArrayList<>();
        timestamps = new ArrayList<>();
        filteredActivities = new ConcurrentHashMap<>();

        Predictor predictor = new Predictor(assetManager);
        FeatureDerivator featureDerivator = new FeatureDerivator();
        SensorService sensorService = new SensorService(sensorManager, powerManager);
        //Logger logger = new Logger(this); TODO

        //observers.add(sensorService);
        //observers.add(featureDerivator);
        //observers.add(textToSpeechManager);

        observers.add(predictor);
        observers.add(textToSpeechManager);
        observers.add(databaseHandler);
        observers.add(sensorService);
        observers.add(featureDerivator);

        //predictor.addObserver(textToSpeechManager);
        predictor.addObserver(this);
        featureDerivator.addObserver(predictor);
        sensorService.addObserver(featureDerivator);
        //lockScreenReceiver.addObserver(sensorService);
        lockScreenReceiver.addObserver(this);
        databaseHandler.addObserver(textToSpeechManager);

        //databaseHandler.dropTable();
        //observers.add(logger);

        databaseThread = new HandlerThread("Database thread", Process.THREAD_PRIORITY_BACKGROUND);
        databaseThread.start();
        databaseThreadHandler = new Handler(databaseThread.getLooper());

    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof Predictor) {
            if (object instanceof Integer) {
                notifyAll(null);
                activities.add((int) object);
//                String filteredActivity = Activities.classes.get((int) object);
                if(activities.size() % 10 == 0) {
                    timestamps.add(System.currentTimeMillis());
                    notifyAll(Activities.getNameOf((int) object));
                }
                //notifyAll(filteredActivities);
//                filteredActivities.clear();
                if(activities.size() >= 30) { // 10, 5, 12, at least 3
                    filterActivities();
                }

            }
            //notifyAll(object);
//            if(observable instanceof SensorService) {
//                if(object instanceof double[]) {
//                    double [] data = (double [])object;
//                    notifyAll(object);
//                }
//            } else if(observable instanceof FeatureDerivator) {
//                if(object instanceof Features) {
//                    notifyAll(object);
//                }
//            } else if(observable instanceof Predictor) {
//                if(object instanceof String) {
//                    notifyAll(object);
//                }
            //}
        } else if(observable instanceof LockScreenReceiver) {
            if(object instanceof Boolean) {
                boolean screenOn = (boolean)object;
                if(screenOn) {
                    filterActivities();
                }
                notifyAll(shouldMeasure && screenOn);
            }
        } else if(observable instanceof MainActivity) {
            if(object instanceof Boolean) {
                shouldMeasure = (boolean)object;
            }
        }

    }

    @Override
    public void notifyAll(Object object) {
        for (Observer observer: observers) {
            observer.update(this, object);
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    private void filterActivities() {
        try {
            int counter = 0;
            int currentActivity = activities.get(0);
            //for(int activity: activities) {
//                    for(int i = 1; i < activities.size(); i++) {
//                        if(currentActivity == activities.get(i)) {
//                            counter++;
//                        } else {
//                            if(counter < 3) {
//                                for(int j = i - (counter+1); j < i; j++) {
//                                    activities.set(j, -1);
//                                }
//                            }
//                            currentActivity = activities.get(i);
//                            counter = 0;
//                        }
//                    }
//                    counter = 0;
//                    for(int i = 0; i < activities.size(); i++) {
//                        if(activities.get(i) == -1) {
//                            counter++;
//                        } else {
//                            for(int j = i- counter; j < counter; j++) {
//                                activities.set(j, activities.get(i));
//                            }
//                            counter = 0;
//                        }
//
//                    }
            int tenthPart = activities.size() / 10;
            for (int i = 0; i < tenthPart; i++) {
                int[] countsArray = new int[6];
                for (int j = 0; j < 10; j++) {
                    int tenthIndex = i * 10;
                    countsArray[activities.get(tenthIndex + j)]++;
                }
                List counts = ArrayUtils.arrayToList(countsArray);

                String filteredActivity = Activities.classes.get(counts.indexOf(Collections.max(counts)));
                filteredActivities.put(timestamps.get(i), filteredActivity);
            }

            //notifyAll("Before database");
            //final Map map = filteredActivities;
            // databaseThreadHandler.post(() ->
            notifyAll(filteredActivities);
            //);
            //activities.remove(0);
        } catch(Exception e) {
            e.printStackTrace();
        }
        filteredActivities.clear();
        activities.clear();
        timestamps.clear();
    }

//    private void addData(double [] data) {
//        //Long now = System.currentTimeMillis();
//        //Long timeDifference = now - previousTime;
//
//        notifyAll(data);
//        //if(timeDifference > WORKING_RATE) { // && previousTime != 0) { //every 2 seconds
//        //notifyAll(null);
//        //previousTime = now;
//        //}
//    }

}
