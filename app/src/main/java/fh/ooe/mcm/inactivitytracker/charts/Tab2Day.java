package fh.ooe.mcm.inactivitytracker.charts;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.renderer.XAxisRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import fh.ooe.mcm.inactivitytracker.R;
import fh.ooe.mcm.inactivitytracker.utils.ChartingUtils;
import fh.ooe.mcm.inactivitytracker.utils.DatabaseHandler;

public class Tab2Day extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2day, container, false);

        LineChart lineChart = rootView.findViewById(R.id.linechart);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(25);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawLabels(true);

        long currTimestamp = System.currentTimeMillis();
        long prevTiemstamp = currTimestamp - 24 * 60 * 60 * 1000;

        DatabaseHandler databaseHandler = new DatabaseHandler(this.getContext());
        Map<Long, String> activitiesForDays =
                databaseHandler.getAllPhysicalActivitiesForDays(prevTiemstamp, currTimestamp);
        lineChart.setData(ChartingUtils.lineChart(activitiesForDays, 1000 * 60 * 60));

        PieChart pieChart = rootView.findViewById(R.id.pie_chart);
        pieChart.setData(ChartingUtils.pieChart(activitiesForDays));
        pieChart.animateY(1000);

        return rootView;
    }
}
