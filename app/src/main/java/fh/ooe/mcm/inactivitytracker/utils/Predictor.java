package fh.ooe.mcm.inactivitytracker.utils;

import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fh.ooe.mcm.inactivitytracker.Exceptions.ModelNotLoadedException;
import fh.ooe.mcm.inactivitytracker.Features;
import fh.ooe.mcm.inactivitytracker.interfaces.Observable;
import fh.ooe.mcm.inactivitytracker.interfaces.Observer;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

public class Predictor implements Observer, Observable {

    ArrayList<Observer> observers;
    private Classifier classifier = null;

    public Predictor(Observer observer, AssetManager assetManager) {
        observers = new ArrayList<>();
        observers.add(observer);
        try{
            classifier = (Classifier) weka.core.SerializationHelper.read(assetManager.open("activityRecognition_RF2.model"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable observable, Object object) {
        if(observable instanceof Recognizer) {
            if(object instanceof Features) {
                Features features = (Features)object;
                try {
                    predict(features);
                } catch(Exception e) {
                    //how to display the error from here ? ;x
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

        final ArrayList<Attribute> xBins = new ArrayList<>();
        final ArrayList<Attribute> yBins = new ArrayList<>();
        final ArrayList<Attribute> zBins = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            xBins.add(new Attribute("X" + i));
        }
        for (int i = 0; i < 10; i++) {
            yBins.add(new Attribute("Y" + i));
        }
        for (int i = 0; i < 10; i++) {
            zBins.add(new Attribute("Z" + i));
        }
        final Attribute xAvg = new Attribute("XAVG");
        final Attribute yAvg = new Attribute("YAVG");
        final Attribute zAvg = new Attribute("ZAVG");
        final Attribute xMedian = new Attribute("XMED");
        final Attribute yMedian = new Attribute("YMED");
        final Attribute zMedian = new Attribute("ZMED");
        final Attribute xMax = new Attribute("XMAX");
        final Attribute yMax = new Attribute("YMAX");
        final Attribute zMax = new Attribute("ZMAX");
        final Attribute xMin = new Attribute("XMIN");
        final Attribute yMin = new Attribute("YMIN");
        final Attribute zMin = new Attribute("ZMIN");
        final Attribute xStandDev = new Attribute("XSTANDDEV");
        final Attribute yStandDev = new Attribute("YSTANDDEV");
        final Attribute zStandDev = new Attribute("ZSTANDDEV");
        final Attribute resultant = new Attribute("RESULTANT");
        final List<String> classes = new ArrayList<String>() {
            {
                add("Downstairs");// cls nr 1
                add("Jogging"); // cls nr 2
                add("Sitting"); // cls nr 3
                add("Standing"); // cls nr 4
                add("Upstairs"); // cls nr 5
                add("Walking"); // cls nr 6
            }
        };

        ArrayList<Attribute> attributeList = new ArrayList<Attribute>(2) {
            {
                for (Attribute bin : xBins) {
                    add(bin);
                }
                for (Attribute bin : yBins) {
                    add(bin);
                }
                for (Attribute bin : zBins) {
                    add(bin);
                }
                add(xAvg);
                add(yAvg);
                add(zAvg);
                add(xMedian);
                add(yMedian);
                add(zMedian);
                add(xMax);
                add(yMax);
                add(zMax);
                add(xMin);
                add(yMin);
                add(zMin);
                add(xStandDev);
                add(yStandDev);
                add(zStandDev);
                add(resultant);
                Attribute attributeClass = new Attribute("@@class@@", classes);
                add(attributeClass);
            }
        };
        Instances dataUnpredicted = new Instances("TestInstances",
                attributeList, 1);

        dataUnpredicted.setClassIndex(dataUnpredicted.numAttributes() - 1);

        DenseInstance newInstance = new DenseInstance(dataUnpredicted.numAttributes()) {
            {
                double [] featuresXBins = features.getXBins();
                double [] featuresYBins = features.getYBins();
                double [] featuresZBins = features.getZBins();

                for (int i = 0; i < 10; i++) {
                    setValue(xBins.get(i), featuresXBins[i]);
                }
                for (int i = 0; i < 10; i++) {
                    setValue(xBins.get(i), featuresYBins[i]);
                }
                for (int i = 0; i < 10; i++) {
                    setValue(xBins.get(i), featuresZBins[i]);
                }
                setValue(xAvg, features.getXAvg());
                setValue(yAvg, features.getYAvg());
                setValue(zAvg, features.getZAvg());
                setValue(xAvg, features.getXMedian());
                setValue(yAvg, features.getYMedian());
                setValue(zAvg, features.getZMedian());
                setValue(xAvg, features.getXMax());
                setValue(yAvg, features.getYMax());
                setValue(zAvg, features.getZMax());
                setValue(xAvg, features.getXMin());
                setValue(yAvg, features.getYMin());
                setValue(zAvg, features.getZMin());
                setValue(xStandDev, features.getXStdDev());
                setValue(yStandDev, features.getYStdDev());
                setValue(zStandDev, features.getZStdDev());
                setValue(resultant, features.getResultant());
            }
        };

        newInstance.setDataset(dataUnpredicted);

        try {
            double result = classifier.classifyInstance(newInstance);
            String className = classes.get(new Double(result).intValue());
            notifyAll(className);
            Log.i("Class:", className);
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
