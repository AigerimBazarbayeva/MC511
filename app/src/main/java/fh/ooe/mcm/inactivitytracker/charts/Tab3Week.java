package fh.ooe.mcm.inactivitytracker.charts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Map;

import fh.ooe.mcm.inactivitytracker.R;
import fh.ooe.mcm.inactivitytracker.utils.ChartingUtils;
import fh.ooe.mcm.inactivitytracker.utils.DatabaseHandler;

public class Tab3Week extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2day, container, false);

        LineChart lineChart = rootView.findViewById(R.id.linechart);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(24 * 7 + 1);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawLabels(true);

        long currTimestamp = System.currentTimeMillis();
        long prevTiemstamp = currTimestamp - 7 * 24 * 60 * 60 * 1000;

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
