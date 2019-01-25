package fh.ooe.mcm.inactivitytracker.utils;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import fh.ooe.mcm.inactivitytracker.PhysicalActivities;
import fh.ooe.mcm.inactivitytracker.models.PersonalData;

public class CaloriesCalculator {

    static final int TEN_SECONDS_IN_30_MINS = 180;

    public class CaloriesBurnedPerKiloFor30MinsOf {
        static final float DOWNSTAIRS = 1.5f;
        static final float JOGGING = 3.161764706f;
        static final float SITTING = 0.851673f;
        static final float STANDING = 1.187944843f;
        static final float UPSTAIRS = 3.161764706f;
        static final float WALKING = 2.380438064f;
    }

    static float[] caloriesBurnedForActivity = {
            CaloriesBurnedPerKiloFor30MinsOf.DOWNSTAIRS,
            CaloriesBurnedPerKiloFor30MinsOf.JOGGING,
            CaloriesBurnedPerKiloFor30MinsOf.SITTING,
            CaloriesBurnedPerKiloFor30MinsOf.STANDING,
            CaloriesBurnedPerKiloFor30MinsOf.UPSTAIRS,
            CaloriesBurnedPerKiloFor30MinsOf.WALKING
    };

    public static double calculateBMR(PersonalData data) {
        double bmr = 0;
        switch(data.getGender()) {
            case MALE:
                bmr = 13.397 * Double.valueOf(data.getWeight()) +
                      4.799 * Integer.valueOf(data.getHeight()) -
                      5.677 * Integer.valueOf(data.getAge()) + 88.362;
            break;
            case FEMALE:
                bmr = 9.247 *  Double.valueOf(data.getWeight()) +
                        3.098 * Integer.valueOf(data.getHeight()) -
                        4.330 * Integer.valueOf(data.getAge()) + 447.593;
            break;
        }

        return bmr;
    }

    public static double getCaloriesBurned(List<String> activities, double kilograms) {
        double caloriesBurned = 0.0f;

        for(int i = 0; i < PhysicalActivities.classes.size(); i++) {
            int count = Collections.frequency(activities, PhysicalActivities.classes.get(i));
            caloriesBurned += (float)count/TEN_SECONDS_IN_30_MINS
                    *caloriesBurnedForActivity[i];
        }

        return caloriesBurned*kilograms;
    }
}
