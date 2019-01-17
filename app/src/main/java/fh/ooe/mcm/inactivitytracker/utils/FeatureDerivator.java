package fh.ooe.mcm.inactivitytracker.utils;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import fh.ooe.mcm.inactivitytracker.Features;
import fh.ooe.mcm.inactivitytracker.interfaces.Observable;
import fh.ooe.mcm.inactivitytracker.interfaces.Observer;

public class FeatureDerivator implements Observer, Observable {

    static {
        System.loadLibrary("native-lib");
    }

    @SuppressWarnings("JniMissingFunction")
    private native double[] smooth(double[] data, int width, int degree);

    final int WINDOW_SIZE = 40;

    ArrayList<Double> xData;
    ArrayList<Double> yData;
    ArrayList<Double> zData;

    ArrayList<Observer> observers;

    public FeatureDerivator() {
        observers = new ArrayList<>();

        xData = new ArrayList<>();
        yData = new ArrayList<>();
        zData = new ArrayList<>();

    }

    public void addData(double [] data) {
        double x = data[0];
        double y = data[1];
        double z = data[2];

        xData.add(xData.size(), x);
        yData.add(yData.size(), y);
        zData.add(zData.size(), z);
    }

    public void makePrediction() {
       // if(xData.size() > WINDOW_SIZE){

//            xData.remove(0);
//            yData.remove(0);
//            zData.remove(0);

            notifyAll(deriveFeatures());

            Iterator it = xData.iterator();
            int i = 0;
            while(it.hasNext() && i++ <= 20) {
                it.next();
                it.remove();
            }
            i = 0;
            it = yData.iterator();
            while(it.hasNext() && i++ <= 20) {
                it.next();
                it.remove();
            }
            i = 0;
            it = zData.iterator();
            while(it.hasNext() && i++ <= 20) {
                it.next();
                it.remove();
            }
//            i = 0;
//            it = times.iterator();
//            while(it.hasNext() && i++ <= 10) {
//                it.next();
//                it.remove();
//            }
//        xData.clear();
//        yData.clear();
//        zData.clear();
        //}
    }

    public Features deriveFeatures() {
        return new Features(
                ArrayUtils.arrayToList(smooth(ArrayUtils.listToArray(xData), 4, 4)),
                ArrayUtils.arrayToList(smooth(ArrayUtils.listToArray(yData), 4, 4)),
                ArrayUtils.arrayToList(smooth(ArrayUtils.listToArray(zData), 4, 4)));
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof SensorService) {
            if(object instanceof double[]) {
                double [] data = (double [])object;
                addData(data);

                if(xData.size() == WINDOW_SIZE) {
                    makePrediction();
                }
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


}
