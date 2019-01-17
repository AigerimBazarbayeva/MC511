package com.at.fhooe.mhs.mobilehealthsportsapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class Tab1Hour extends Fragment{

    float calories[] = {700, 300};
    String hours[] = {"1pm"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1hour, container, false);
        List<PieEntry> pieEntries = new ArrayList<>();
        for(int i = 0; i <  calories.length; i++){
            pieEntries.add(new PieEntry(calories[i], hours[0]));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Calories per hour");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData = new PieData(pieDataSet);

        PieChart chart = (PieChart) rootView.findViewById(R.id.chart);
        chart.setData(pieData);
        chart.animateY(1000);
        chart.invalidate();
        return rootView;
    }
}
