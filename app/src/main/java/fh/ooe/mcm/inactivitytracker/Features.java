package fh.ooe.mcm.inactivitytracker;

import java.util.ArrayList;

import fh.ooe.mcm.inactivitytracker.utils.StatisticalMathCalculator;

public class Features {
    double xAvg;
    double yAvg;
    double zAvg;

    double xMedian;
    double yMedian;
    double zMedian;

    double xMax;
    double yMax;
    double zMax;

    double xMin;
    double yMin;
    double zMin;

    double xZerocr;
    double yZerocr;
    double zZerocr;

    double resultant;

    public Features(ArrayList<Double> xData, ArrayList<Double> yData, ArrayList<Double> zData) {
        xAvg = StatisticalMathCalculator.calculateAverage(xData);
        yAvg = StatisticalMathCalculator.calculateAverage(yData);
        zAvg = StatisticalMathCalculator.calculateAverage(zData);

        xMedian = StatisticalMathCalculator.calculateMedian(xData);
        yMedian = StatisticalMathCalculator.calculateMedian(yData);
        zMedian = StatisticalMathCalculator.calculateMedian(zData);

        xMax = StatisticalMathCalculator.calculateMax(xData);
        yMax = StatisticalMathCalculator.calculateMax(yData);
        zMax = StatisticalMathCalculator.calculateMax(zData);

        xMin = StatisticalMathCalculator.calculateMin(xData);
        yMin = StatisticalMathCalculator.calculateMin(yData);
        zMin = StatisticalMathCalculator.calculateMin(zData);

        xZerocr = StatisticalMathCalculator.calculateZerocr(xData);
        yZerocr = StatisticalMathCalculator.calculateZerocr(yData);
        zZerocr = StatisticalMathCalculator.calculateZerocr(zData);

        resultant = StatisticalMathCalculator.calculateResultant(xData, yData, zData);
    }

    public double getXMedian() {
        return xMedian;
    }

    public double getYMedian() {
        return yMedian;
    }

    public double getZMedian() {
        return zMedian;
    }

    public double getXMax() {
        return xMax;
    }

    public double getYMax() {
        return yMax;
    }

    public double getZMax() {
        return zMax;
    }

    public double getXMin() {
        return xMin;
    }

    public double getYMin() {
        return yMin;
    }

    public double getZMin() {
        return zMin;
    }

    public double getXZerocr() {
        return xZerocr;
    }

    public double getYZerocr() {
        return yZerocr;
    }

    public double getZZerocr() {
        return zZerocr;
    }

    public double getResultant() {
        return resultant;
    }
}