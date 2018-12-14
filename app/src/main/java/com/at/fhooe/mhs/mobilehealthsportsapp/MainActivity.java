package com.at.fhooe.mhs.mobilehealthsportsapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int height;
    int weight;
    String gender;
    int age;

    EditText heightInput;
    EditText weightInput;
    EditText genderInput;
    EditText ageInput;

    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        heightInput = (EditText) findViewById(R.id.heightInput);
        weightInput = (EditText) findViewById(R.id.weightInput);
        genderInput = (EditText) findViewById(R.id.genderInput);
        ageInput = (EditText) findViewById(R.id.ageInput);

        submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                height = Integer.valueOf(heightInput.getText().toString());
                weight = Integer.valueOf(weightInput.getText().toString());
                gender = genderInput.getText().toString();
                age = Integer.valueOf(ageInput.getText().toString());

                double bmrM = 10 * weight + 6.25 * height - 5 * age + 5;
                double bmrF = 10 * weight + 6.25 * height - 5 * age - 161;

                if(gender.equals("F")){
                    showToast("BMR index: " + bmrF);
                }else if(gender.equals("M")){
                    showToast("BMR index: " + bmrM);
                }else{
                    return;
                }

            }
        });

    }

    private void showToast(String text){
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
