package fh.ooe.mcm.inactivitytracker.utils;

import android.util.Log;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartingUtils {

//    public static LineData lineChart(Map<Long, String> activitiesForDays, int divideBy) {
//        long currTimestamp = System.currentTimeMillis();
//        Map<Long, Integer> points = new HashMap<>();
//        for (Map.Entry<Long, String> entry : activitiesForDays.entrySet()) {
//            long hour = (currTimestamp - entry.getKey()) / divideBy;
//            int prev = points.containsKey(hour) ? points.get(hour) : 0;
//            points.put(hour, prev + 1);
//        }
//
//        ArrayList<Entry> yvalues = new ArrayList<>();
//        for (Map.Entry<Long, Integer> entry : points.entrySet()) {
//            yvalues.add(new Entry(entry.getKey(), entry.getValue()));
//        }
//
//        LineDataSet set1 = new LineDataSet(yvalues, "Data set 1");
//        set1.setFillAlpha(110);
//
//        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
//        dataSets.add(set1);
//
//        return  new LineData(dataSets);
//    }

    public static PieData pieChart(Map<Long, String> activitiesForDays) {
        Map<String, Integer> cnts = new HashMap<>();
        for (Map.Entry<Long, String> entry : activitiesForDays.entrySet()) {
            int oldCnt = cnts.containsKey(entry.getValue()) ? cnts.get(entry.getValue()) : 0;
            cnts.put(entry.getValue(), oldCnt + 1);
        }

        List<PieEntry> pieEntries = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : cnts.entrySet()) {
            pieEntries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Activities");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        return new PieData(pieDataSet);
    }
}
