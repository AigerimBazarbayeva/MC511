package fh.ooe.mcm.inactivitytracker.utils;

import android.content.res.AssetManager;
import android.hardware.SensorManager;
import android.os.PowerManager;

import java.util.ArrayList;

import fh.ooe.mcm.inactivitytracker.Features;
import fh.ooe.mcm.inactivitytracker.interfaces.Observable;
import fh.ooe.mcm.inactivitytracker.interfaces.Observer;

public class Recognizer implements Observer, Observable {

    final int WORKING_RATE = 200; // two seconds

    ArrayList<Observer> observers;
    long previousTime = 0;

    public Recognizer(SensorManager sensorManager, PowerManager powerManager, AssetManager assetManager) {
        observers = new ArrayList<>();

        SensorService sensorService = new SensorService(this, sensorManager, powerManager);

        FeatureDerivator featureDerivator = new FeatureDerivator(this);
        Predictor predictor = new Predictor(this, assetManager);
        //Logger logger = new Logger(this); TODO

        observers.add(sensorService);
        observers.add(featureDerivator);
        observers.add(predictor);
        sensorService.addObserver(featureDerivator);
        //observers.add(logger);
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof SensorService) {
            if(object instanceof double[]) {
                double [] data = (double [])object;
                addData(data);
            }
        } else if(observable instanceof FeatureDerivator) {
            if(object instanceof Features) {
                notifyAll(object);
            }
        } else if(observable instanceof Predictor) {
            if(object instanceof String) {
                notifyAll(object);
            }
        } else if(observable instanceof LockScreenReceiver) {
            notifyAll(false);
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

    private void addData(double [] data) {
        Long now = System.currentTimeMillis();
        Long timeDifference = now - previousTime;

        notifyAll(data);
        if(timeDifference > WORKING_RATE) { // && previousTime != 0) { //every 2 seconds
            notifyAll(null);
            previousTime = now;
        }
    }

}
