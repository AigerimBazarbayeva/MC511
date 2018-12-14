package fh.ooe.mcm.inactivitytracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fh.ooe.mcm.inactivitytracker.utils.StatisticalMathCalculator;

public class Features {
    double [] xBins;
    double [] yBins;
    double [] zBins;

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

    double xStdDev;
    double yStdDev;
    double zStdDev;

    double resultant;

    public Features(ArrayList<Double> xData, ArrayList<Double> yData, ArrayList<Double> zData) {
        xBins = StatisticalMathCalculator.calculateBins(xData);
        yBins = StatisticalMathCalculator.calculateBins(yData);
        zBins = StatisticalMathCalculator.calculateBins(zData);

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

        xStdDev = StatisticalMathCalculator.calculateStandardDeviation(xData, xAvg);
        yStdDev = StatisticalMathCalculator.calculateStandardDeviation(yData, yAvg);
        zStdDev = StatisticalMathCalculator.calculateStandardDeviation(zData, zAvg);

        resultant = StatisticalMathCalculator.calculateResultant(xData, yData, zData);
    }

    public double[] getXBins() {
        return xBins;
    }

    public double[] getYBins() {
        return yBins;
    }

    public double[] getZBins() {
        return zBins;
    }

    public double getXAvg() {
        return xAvg;
    }

    public double getYAvg() {
        return yAvg;
    }

    public double getZAvg() {
        return zAvg;
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

    public double getXStdDev() {
        return xStdDev;
    }

    public double getYStdDev() {
        return yStdDev;
    }

    public double getZStdDev() {
        return zStdDev;
    }

    public double getResultant() {
        return resultant;
    }
}