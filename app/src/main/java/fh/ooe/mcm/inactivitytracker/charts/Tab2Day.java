package fh.ooe.mcm.inactivitytracker.charts;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Random;

import fh.ooe.mcm.inactivitytracker.R;

public class Tab2Day extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2day, container, false);

        LineChart lineChart = (LineChart) rootView.findViewById(R.id.linechart);

        ArrayList<Entry> yvalues = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < 24; i++){
            yvalues.add(new Entry(i, random.nextInt(3) + 1));
        }

        LineDataSet set1 = new LineDataSet(yvalues, "Data set 1");
        set1.setFillAlpha(110);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        lineChart.setData(data);
        return rootView;
    }
}
