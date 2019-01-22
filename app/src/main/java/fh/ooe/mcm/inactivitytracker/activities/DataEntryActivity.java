package fh.ooe.mcm.inactivitytracker.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import fh.ooe.mcm.inactivitytracker.R;

public class DataEntryActivity extends AppCompatActivity {

    final String PERSONAL_DATA_KEY = "PERSONAL_DATA";
    final String AGE_KEY = "AGE";
    final String GENDER_KEY = "GENDER";
    final String HEIGHT_KEY = "HEIGHT";
    final String WEIGHT_KEY = "WEIGHT";
    final String CALORIES_PER_DAY_KEY = "CALORIES_PER_DAY";

    EditText age;
    RadioGroup gender;
    EditText height;
    EditText weight;
    EditText caloriesPerDay;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);

        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        caloriesPerDay = findViewById(R.id.caloriesPerDay);

        sharedPreferences = getSharedPreferences(PERSONAL_DATA_KEY, Context.MODE_PRIVATE);

        if(sharedPreferences.contains(AGE_KEY)) {
            age.setText(sharedPreferences.getString(AGE_KEY, ""));
        }
        if(sharedPreferences.contains(GENDER_KEY)) {
            int id =  sharedPreferences.getInt(GENDER_KEY, 0);
            ((RadioButton) gender.getChildAt(id)).setChecked(true);
        }
        if(sharedPreferences.contains(HEIGHT_KEY)) {
            height.setText(sharedPreferences.getString(HEIGHT_KEY, ""));
        }
        if(sharedPreferences.contains(WEIGHT_KEY)) {
            weight.setText(sharedPreferences.getString(WEIGHT_KEY, ""));
        }
        if(sharedPreferences.contains(CALORIES_PER_DAY_KEY)) {
            caloriesPerDay.setText(sharedPreferences.getString(CALORIES_PER_DAY_KEY, ""));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Editor editor = sharedPreferences.edit();
        editor.putString(AGE_KEY, age.getText().toString());
        editor.putInt(GENDER_KEY, indexOfSelectedGender());
        editor.putString(HEIGHT_KEY, height.getText().toString());
        editor.putString(WEIGHT_KEY, weight.getText().toString());
        editor.putString(CALORIES_PER_DAY_KEY, caloriesPerDay.getText().toString());
        editor.commit();
    }

    private int indexOfSelectedGender() {
       return gender.indexOfChild(gender.findViewById(gender.getCheckedRadioButtonId()));
    }
}
