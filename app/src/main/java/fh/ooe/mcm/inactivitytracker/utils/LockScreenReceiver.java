package fh.ooe.mcm.inactivitytracker.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import fh.ooe.mcm.inactivitytracker.interfaces.Observable;
import fh.ooe.mcm.inactivitytracker.interfaces.Observer;

public class LockScreenReceiver extends BroadcastReceiver implements Observable {

    ArrayList<Observer> observers;

    public LockScreenReceiver() { //Observer observer) {
        observers = new ArrayList<>();
        //observers.add(observer);
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent != null && intent.getAction() != null)
        {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON))
            {
                notifyAll(false);
            }

            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
            {
                notifyAll(true);
            }
        }
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