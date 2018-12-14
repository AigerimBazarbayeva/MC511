package fh.ooe.mcm.inactivitytracker.utils;

import java.util.ArrayList;

import fh.ooe.mcm.inactivitytracker.Features;
import fh.ooe.mcm.inactivitytracker.interfaces.Observable;
import fh.ooe.mcm.inactivitytracker.interfaces.Observer;

public class FeatureDerivator implements Observer, Observable {

    final int WINDOW_SIZE = 25;

    ArrayList<Double> xData;
    ArrayList<Double> yData;
    ArrayList<Double> zData;

    ArrayList<Observer> observers;

    public FeatureDerivator(Observer observer) {
        observers = new ArrayList<>();
        addObserver(observer);

        xData = new ArrayList<>();
        yData = new ArrayList<>();
        zData = new ArrayList<>();
    }

    public void addData(double [] data) {
        double x = data[0];
        double y = data[1];
        double z = data[2];

        xData.add(x);
        yData.add(y);
        zData.add(z);

        if(xData.size() > WINDOW_SIZE){
            xData.remove(1);
            yData.remove(1);
            zData.remove(1);

            notifyAll(deriveFeatures());
        }
    }

    public Features deriveFeatures() {
        return new Features(xData, yData, zData);
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof SensorService) {
            if(object instanceof double[]) {
                double [] data = (double [])object;
                addData(data);
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
