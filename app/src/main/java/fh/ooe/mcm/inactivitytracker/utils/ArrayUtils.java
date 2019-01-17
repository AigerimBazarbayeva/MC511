package fh.ooe.mcm.inactivitytracker.utils;

import java.util.ArrayList;

public class ArrayUtils {

    public static double[] listToArray(ArrayList<Double> list) {
        double[] array = new double[list.size()];
        int index = 0;

        for(double value : list){
            array[index] = value; // unboxing is automtically done here
            index++;
        }
        return array;
    }

    public static ArrayList<Double> arrayToList(double [] array) {
        ArrayList<Double> list = new ArrayList<>();

        for(int i = 0; i < array.length; i++){
            list.add(array[i]);
        }
        return list;
    }

    public static ArrayList<Integer> arrayToList(int [] array) {
        ArrayList<Integer> list = new ArrayList<>();

        for(int i = 0; i < array.length; i++){
            list.add(array[i]);
        }
        return list;
    }
}
