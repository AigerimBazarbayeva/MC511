package fh.ooe.mcm.inactivitytracker.utils;

import java.util.ArrayList;
import java.util.Collections;

public class StatisticalMathCalculator {
    public static double calculateAverage(ArrayList<Double> data) {
        double avg = 0;
        for (double value : data) {
            avg += value;
        }
        avg = avg / data.size();
        return twoDigits(avg);
    }

    public static double calculateMedian(ArrayList<Double> data) {
        double median = 0;

        Collections.sort(data);
        if(data.size() % 2 != 0) {
            int index = data.size()/2;
            median = (data.get(index) + data.get(index+1))/2;
        } else {
            data.get((int)Math.ceil(data.size()/2));
        }
        return twoDigits(median);
    }

    public static double calculateMax(ArrayList<Double> data) {
        return twoDigits(Collections.max(data));
    }

    public static double calculateMin(ArrayList<Double> data) {
        return twoDigits(Collections.min(data));
    }

    public static double calculateStandardDeviation(ArrayList<Double> data, double avg) {
        double std = 0;
        for (double value : data) {
            std += Math.pow(value - avg, 2);
        }
        std = Math.sqrt(std / data.size());
        return twoDigits(std);
    }

    public static double calculateAbsoluteDeviation(ArrayList<Double> data, double avg) {
        double absDev = 0;
        for (double value : data) {
            absDev += Math.abs(value - avg);
        }
        absDev /= data.size();
        return twoDigits(absDev);
    }

    public static double calculateResultant(ArrayList<Double> xData, ArrayList<Double> yData, ArrayList<Double> zData) {
        double resultant = 0;
        for (int i = 0; i < xData.size(); i++) {
            resultant += Math.sqrt(xData.get(i) * xData.get(i) +
                    yData.get(i) * yData.get(i) +
                    zData.get(i) * zData.get(i));

        }
        resultant /= xData.size();
        return twoDigits(resultant);
    }

    public static double[] calculateBins(ArrayList<Double> data) {
        double[] bins = new double[10];

        double max = Collections.max(data);
        double min = Collections.min(data);

        double range = max - min;
        double bin = range / 10;

        double offset = 0 - min;


        for (double value : data) {
            if (value < max) {
                int index = (int) (Math.abs((value + offset) / bin));
                bins[index]++;
            }
        }

        for (int i = 0; i < bins.length; i++) {
            bins[i] /= (data.size() - 1);
            bins[i] = twoDigits(bins[i]);
        }

        return bins;
    }

    static double twoDigits(double value) {
        return Math.round(value*100.0)/100.0;
    }
}
