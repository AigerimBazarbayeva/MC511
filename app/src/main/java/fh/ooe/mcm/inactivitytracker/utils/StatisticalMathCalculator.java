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
            median = data.get((int)Math.ceil(data.size()/2));
        }
        return twoDigits(median);
    }

    public static double calculateMax(ArrayList<Double> data) {
        return twoDigits(Collections.max(data));
    }

    public static double calculateMin(ArrayList<Double> data) {
        return twoDigits(Collections.min(data));
    }

    public static int calculateZerocr(ArrayList<Double> data) {
        double mean = calculateAverage(data);
        int counter = 0;
        double previousValue = data.get(0) - mean;

        for (int i = 1; i < data.size(); i++) {
            double centeredValue = data.get(i) - mean;
            if(centeredValue <= 0 && previousValue > 0 ||
                    centeredValue >= 0 && previousValue < 0  ) {
                counter++;
            }
        }

        return counter;
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

    static double twoDigits(double value) {
        return Math.round(value*100.0)/100.0;
    }
}
