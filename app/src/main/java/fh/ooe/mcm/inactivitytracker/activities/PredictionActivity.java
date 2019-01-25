package fh.ooe.mcm.inactivitytracker.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fh.ooe.mcm.inactivitytracker.PhysicalActivities;
import fh.ooe.mcm.inactivitytracker.R;
import fh.ooe.mcm.inactivitytracker.models.PersonalData;
import fh.ooe.mcm.inactivitytracker.utils.CaloriesCalculator;
import fh.ooe.mcm.inactivitytracker.utils.DatabaseHandler;
import fh.ooe.mcm.inactivitytracker.utils.PersonalDataPreferences;

public class PredictionActivity extends AppCompatActivity {

    //final static int CALORIES_THRESHOLD = 100;

    SharedPreferences personalDataPreferences;

    enum BodyShapePrediction {
        GETTING_SLIM, STABLE, GETTING_FAT
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);

        TextView predictionText = findViewById(R.id.predictionText);
        ImageView predictionImage = findViewById(R.id.predictionImage);

        personalDataPreferences = getSharedPreferences(PersonalDataPreferences.PREFERENCE_KEY, Context.MODE_PRIVATE);

        PersonalData data = new PersonalData();

        if(personalDataPreferences.contains(PersonalDataPreferences.AGE_KEY)) {
            data.setAge(personalDataPreferences.getString(PersonalDataPreferences.AGE_KEY, ""));
        }
        if(personalDataPreferences.contains(PersonalDataPreferences.GENDER_KEY)) {
            data.setGenderFromInt(personalDataPreferences.getInt(PersonalDataPreferences.GENDER_KEY, 0));
        }
        if(personalDataPreferences.contains(PersonalDataPreferences.HEIGHT_KEY)) {
            data.setHeight(personalDataPreferences.getString(PersonalDataPreferences.HEIGHT_KEY, ""));
        }
        if(personalDataPreferences.contains(PersonalDataPreferences.WEIGHT_KEY)) {
            data.setWeight(personalDataPreferences.getString(PersonalDataPreferences.WEIGHT_KEY, ""));
        }
        if(personalDataPreferences.contains(PersonalDataPreferences.CALORIES_PER_DAY_KEY)) {
            data.setCaloriesPerDay(personalDataPreferences.getString(PersonalDataPreferences.CALORIES_PER_DAY_KEY, ""));
        }
        DatabaseHandler newDatabaseHandler = new DatabaseHandler(this);


        Calendar today = Calendar.getInstance();
        Calendar yesterday = (Calendar) today.clone();
        //lastWeek.add(Calendar.WEEK_OF_YEAR, -1);
        yesterday.set(Calendar.DAY_OF_YEAR, -1);

//            Random r = new Random();
//        for(int i = 0; i < 5760; i++) {
//            newDatabaseHandler.addActivity(yesterday.getTimeInMillis(),PhysicalActivities.classes.get(r.nextInt(6)));
//        }

        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);
        yesterday.set(Calendar.MILLISECOND, 0);
        Map<Long, String> activities = newDatabaseHandler
                .getAllPhysicalActivitiesForDays(
                        yesterday.getTimeInMillis(),
                        today.getTimeInMillis());

        List<String> a = new ArrayList(activities.values());

        double caloriesPerDay = Double.valueOf(data.getCaloriesPerDay());
        double caloriesBurned = CaloriesCalculator.getCaloriesBurned(a, Double.valueOf(data.getWeight()));
        //double bmr = CaloriesCalculator.calculateBMR(data);

        int comparisonResult = Double.compare(caloriesPerDay, caloriesBurned);

        BodyShapePrediction predictionResult;

        if(comparisonResult > 0) {
            predictionResult = BodyShapePrediction.GETTING_FAT;
        }else if (comparisonResult == 0) {
            predictionResult = BodyShapePrediction.STABLE;
        } else {
            predictionResult = BodyShapePrediction.GETTING_SLIM;
        }

        switch(predictionResult) {
            case GETTING_SLIM:
                predictionText.setText(R.string.getting_slim);
                predictionImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.getting_slim));
            break;

            case STABLE:
                predictionText.setText(R.string.stable);
                predictionImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.stable));
            break;

            case GETTING_FAT:
                predictionText.setText(R.string.getting_fat);
                predictionImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.getting_fat));

            break;

        }
    }


}
