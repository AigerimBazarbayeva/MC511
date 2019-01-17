package fh.ooe.mcm.inactivitytracker.charts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import fh.ooe.mcm.inactivitytracker.R;

public class Tab3Week extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3week, container, false);

        BarChart barChart = (BarChart) rootView.findViewById(R.id.barchart);

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setMaxVisibleValueCount(100);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);

        ArrayList<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(1, 40f));
        barEntries.add(new BarEntry(2, 50f));
        barEntries.add(new BarEntry(3, 20f));
        barEntries.add(new BarEntry(4, 30f));
        barEntries.add(new BarEntry(5, 40f));
        barEntries.add(new BarEntry(6, 60f));
        barEntries.add(new BarEntry(7, 10f));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Data set 1");
        barDataSet.setColors(ColorTemplate.getHoloBlue());

        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.9f);

        barChart.setData(data);
        return rootView;
    }
}
