package fh.ooe.mcm.inactivitytracker;

import java.util.ArrayList;
import java.util.List;

public class PhysicalActivities {

    public final static List<String> classes = new ArrayList<String>() {
        {
            add("Downstairs");// cls nr 1
            add("Jogging"); // cls nr 2
            add("Sitting"); // cls nr 3
            add("Standing"); // cls nr 4
            add("Upstairs"); // cls nr 5
            add("Walking"); // cls nr 6
        }
    };

    public final static String getNameOf(int i) {
        return classes.get(i);
    }

}
