package fh.ooe.mcm.inactivitytracker.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RadioButton;

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

        if(personalDataPreferences.contains(AGE_KEY)) {
            data.setAge(personalDataPreferences.getString(AGE_KEY, ""));
        }
        if(personalDataPreferences.contains(GENDER_KEY)) {
            data.setGenderFromInt(personalDataPreferences.getInt(GENDER_KEY, 0));
        }
        if(personalDataPreferences.contains(HEIGHT_KEY)) {
            data.setHeight(personalDataPreferences.getString(HEIGHT_KEY, ""));
        }
        if(personalDataPreferences.contains(WEIGHT_KEY)) {
            data.setWeight(personalDataPreferences.getString(WEIGHT_KEY, ""));
        }
        if(personalDataPreferences.contains(CALORIES_PER_DAY_KEY)) {
            data.setCaloriesPerDay(personalDataPreferences.getString(CALORIES_PER_DAY_KEY, ""));
        }

        return data;
    }

}
