package fh.ooe.mcm.inactivitytracker.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
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

    SharedPreferences personalDataPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);

        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        caloriesPerDay = findViewById(R.id.caloriesPerDay);

        //PersonalData data = PersonalDataPreferences.getPersonalData(this);

//        age.setText(data.getAge());
//        int id = data.getGenderAsInteger();
//        ((RadioButton) gender.getChildAt(id)).setChecked(true);
//        height.setText(data.getHeight());
//        weight.setText(data.getWeight());
//        caloriesPerDay.setText(data.getCaloriesPerDay());
        personalDataPreferences = getSharedPreferences(PersonalDataPreferences.PREFERENCE_KEY, Context.MODE_PRIVATE);

        if(personalDataPreferences.contains(PersonalDataPreferences.AGE_KEY)) {
            age.setText(personalDataPreferences.getString(PersonalDataPreferences.AGE_KEY, ""));
        }
        if(personalDataPreferences.contains(PersonalDataPreferences.GENDER_KEY)) {
            int id =  personalDataPreferences.getInt(PersonalDataPreferences.GENDER_KEY, 0);
            ((RadioButton) gender.getChildAt(id)).setChecked(true);
        }
        if(personalDataPreferences.contains(PersonalDataPreferences.HEIGHT_KEY)) {
            height.setText(personalDataPreferences.getString(PersonalDataPreferences.HEIGHT_KEY, ""));
        }
        if(personalDataPreferences.contains(PersonalDataPreferences.WEIGHT_KEY)) {
            weight.setText(personalDataPreferences.getString(PersonalDataPreferences.WEIGHT_KEY, ""));
        }
        if(personalDataPreferences.contains(PersonalDataPreferences.CALORIES_PER_DAY_KEY)) {
            caloriesPerDay.setText(personalDataPreferences.getString(PersonalDataPreferences.CALORIES_PER_DAY_KEY, ""));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        Editor editor = personalDataPreferences.edit();
        editor.putString(PersonalDataPreferences.AGE_KEY, age.getText().toString());
        editor.putInt(PersonalDataPreferences.GENDER_KEY, indexOfSelectedGender());
        editor.putString(PersonalDataPreferences.HEIGHT_KEY, height.getText().toString());
        editor.putString(PersonalDataPreferences.WEIGHT_KEY, weight.getText().toString());
        editor.putString(PersonalDataPreferences.CALORIES_PER_DAY_KEY, caloriesPerDay.getText().toString());
        editor.commit();
    }

    private int indexOfSelectedGender() {
       return gender.indexOfChild(gender.findViewById(gender.getCheckedRadioButtonId()));
    }
}
