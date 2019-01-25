package fh.ooe.mcm.inactivitytracker.models;

import android.widget.EditText;
import android.widget.RadioGroup;

public class PersonalData {

    public enum Gender {
        MALE, FEMALE
    }

    String age;
    Gender gender;
    String height;
    String weight;
    String caloriesPerDay;

    public String getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public int getGenderAsInteger() {
        return gender.ordinal();
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getCaloriesPerDay() {
        return caloriesPerDay;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setGenderFromInt(int gender) {
        this.gender = Gender.values()[gender];
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setCaloriesPerDay(String caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
    }
}
