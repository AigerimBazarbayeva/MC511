package fh.ooe.mcm.inactivitytracker.utils;

import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fh.ooe.mcm.inactivitytracker.PhysicalActivities;
import fh.ooe.mcm.inactivitytracker.Features;
import fh.ooe.mcm.inactivitytracker.exceptions.ModelNotLoadedException;
import fh.ooe.mcm.inactivitytracker.interfaces.Observable;
import fh.ooe.mcm.inactivitytracker.interfaces.Observer;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class ActivityClassPredictor implements Observer, Observable {

    ArrayList<Observer> observers;
    private Classifier classifier = null;

    public ActivityClassPredictor(AssetManager assetManager) {
        observers = new ArrayList<>();

        try{
            classifier = (Classifier) weka.core.SerializationHelper.read(assetManager.open("activityRecognition_RF_2sec.model"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof FeatureDerivator) {
            if(object instanceof Features) {
                Features features = (Features)object;
                try {
                    predict(features);
                } catch(Exception e) {
                    //how to display the error from here ? ;x
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    private void predict(final Features features) throws ModelNotLoadedException {
        if (classifier == null) {
            throw new ModelNotLoadedException();
            //Toast.makeText(this, "Model not loaded!", Toast.LENGTH_SHORT).show();
        }

        final Attribute xMedian = new Attribute("XMED");
        final Attribute yMedian = new Attribute("YMED");
        final Attribute zMedian = new Attribute("ZMED");
        final Attribute xMax = new Attribute("XMAX");
        final Attribute yMax = new Attribute("YMAX");
        final Attribute zMax = new Attribute("ZMAX");
        final Attribute xMin = new Attribute("XMIN");
        final Attribute yMin = new Attribute("YMIN");
        final Attribute zMin = new Attribute("ZMIN");
        final Attribute xZerocr = new Attribute("ZEROCRX");
        final Attribute yZerocr = new Attribute("ZEROCRY");
        final Attribute zZerocr = new Attribute("ZEROCRZ");
        final Attribute resultant = new Attribute("RESULTANT");


        ArrayList<Attribute> attributeList = new ArrayList<Attribute>(2) {
            {
                add(xMedian);
                add(yMedian);
                add(zMedian);
                add(xMax);
                add(yMax);
                add(zMax);
                add(xMin);
                add(yMin);
                add(zMin);
                add(xZerocr);
                add(yZerocr);
                add(zZerocr);
                add(resultant);
                Attribute attributeClass = new Attribute("@@class@@", PhysicalActivities.classes);
                add(attributeClass);
            }
        };
        Instances dataUnpredicted = new Instances("TestInstances",
                attributeList, 1);

        dataUnpredicted.setClassIndex(dataUnpredicted.numAttributes() - 1);

        DenseInstance newInstance = new DenseInstance(dataUnpredicted.numAttributes()) {
            {
                setValue(xMedian, features.getXMedian());
                setValue(yMedian, features.getYMedian());
                setValue(zMedian, features.getZMedian());
                setValue(xMax, features.getXMax());
                setValue(yMax, features.getYMax());
                setValue(zMax, features.getZMax());
                setValue(xMin, features.getXMin());
                setValue(yMin, features.getYMin());
                setValue(zMin, features.getZMin());
                setValue(xZerocr, features.getXZerocr());
                setValue(yZerocr, features.getYZerocr());
                setValue(zZerocr, features.getZZerocr());
                setValue(resultant, features.getResultant());
            }
        };

        newInstance.setDataset(dataUnpredicted);

        try {
            int result = new Double(classifier.classifyInstance(newInstance)).intValue();
            notifyAll(result);
            Log.i("Class:", PhysicalActivities.getNameOf(result) + System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyAll(Object object) {
        for (Observer observer: observers) {
            observer.update(this, object);
        }
    }
}
