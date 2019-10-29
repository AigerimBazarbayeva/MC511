package fh.ooe.mcm.inactivitytracker.utils;

import android.content.Context;
import android.content.SharedPreferences;

import fh.ooe.mcm.inactivitytracker.models.PersonalData;

public class PersonalDataPreferences {

    public static final String PREFERENCE_KEY = "PERSONAL_DATA";
    public static final String AGE_KEY = "AGE";
    public static final String GENDER_KEY = "GENDER";
    public static final String HEIGHT_KEY = "HEIGHT";
    public static final String WEIGHT_KEY = "WEIGHT";
    public static final String CALORIES_PER_DAY_KEY = "CALORIES_PER_DAY";

    public static PersonalData getPersonalData(Context context) {
        SharedPreferences personalDataPreferences = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);

        PersonalData data = new PersonalData();

        if (personalDataPreferences.contains(AGE_KEY)) {
            String age = personalDataPreferences.getString(AGE_KEY, "0");
            if(age.isEmpty()) age = "0";
            data.setAge(Integer.parseInt(age));
        }
        if (personalDataPreferences.contains(GENDER_KEY)) {
            data.setGenderFromInt(personalDataPreferences.getInt(GENDER_KEY, 0));
        }
        if (personalDataPreferences.contains(HEIGHT_KEY)) {
            String height = personalDataPreferences.getString(HEIGHT_KEY, "0");
            if(height.isEmpty()) height = "0";
            data.setHeight(Integer.parseInt(height));
        }
        if (personalDataPreferences.contains(WEIGHT_KEY)) {
            String weight = personalDataPreferences.getString(WEIGHT_KEY, "0");
            if(weight.isEmpty()) weight = "0";
            data.setWeight(Float.parseFloat(weight));
        }
        if (personalDataPreferences.contains(CALORIES_PER_DAY_KEY)) {
            String calendarPerDay = personalDataPreferences.getString(CALORIES_PER_DAY_KEY, "0");
            if(calendarPerDay.isEmpty()) calendarPerDay = "0";
            data.setCaloriesPerDay(Integer.parseInt(calendarPerDay));
        }

        return data;
    }

}
