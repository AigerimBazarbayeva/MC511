package fh.ooe.mcm.inactivitytracker.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import fh.ooe.mcm.inactivitytracker.R;
import fh.ooe.mcm.inactivitytracker.models.PersonalData;
import fh.ooe.mcm.inactivitytracker.utils.PersonalDataPreferences;

public class DataEntryActivity extends AppCompatActivity {

    EditText age;
    RadioGroup gender;
    EditText height;
    EditText weight;
    EditText caloriesPerDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);

        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        caloriesPerDay = findViewById(R.id.caloriesPerDay);

        PersonalData data = PersonalDataPreferences.getPersonalData(this);

        age.setText(data.getAgeAsString());
        int id = data.getGenderAsInteger();
        ((RadioButton) gender.getChildAt(id)).setChecked(true);
        height.setText(data.getHeightAsString());
        weight.setText(data.getWeightAsString());
        caloriesPerDay.setText(data.getCaloriesPerDayAsString());
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences personalDataPreferences = getSharedPreferences(PersonalDataPreferences.PREFERENCE_KEY, Context.MODE_PRIVATE);
        if(personalDataPreferences != null) {
            Editor editor = personalDataPreferences.edit();
            editor.putString(PersonalDataPreferences.AGE_KEY, age.getText().toString());
            editor.putInt(PersonalDataPreferences.GENDER_KEY, indexOfSelectedGender());
            editor.putString(PersonalDataPreferences.HEIGHT_KEY, height.getText().toString());
            editor.putString(PersonalDataPreferences.WEIGHT_KEY, weight.getText().toString());
            editor.putString(PersonalDataPreferences.CALORIES_PER_DAY_KEY, caloriesPerDay.getText().toString());
            editor.commit();
        }
    }

    private int indexOfSelectedGender() {
       return gender.indexOfChild(gender.findViewById(gender.getCheckedRadioButtonId()));
    }
}
