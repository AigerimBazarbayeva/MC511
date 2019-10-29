package fh.ooe.mcm.inactivitytracker.models;

public class PersonalData {

    public enum Gender {
        MALE, FEMALE
    }

    int age;
    Gender gender = Gender.MALE;
    int height;
    float weight;
    int caloriesPerDay;

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public int getGenderAsInteger() {
        return gender.ordinal();
    }

    public int getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public int getCaloriesPerDay() {
        return caloriesPerDay;
    }

    public String getAgeAsString() {
        return age > 0 ? String.valueOf(age) : "";
    }

    public String getGenderAsString() {
        return gender.toString();
    }

    public String getHeightAsString() {
        return height > 0 ? String.valueOf(height) : "";
    }

    public String getWeightAsString() {
        return weight > 0 ? String.valueOf(weight) : "";
    }

    public String getCaloriesPerDayAsString() {
        return caloriesPerDay > 0 ? String.valueOf(caloriesPerDay) : "";
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setGenderFromInt(int gender) {
        this.gender = Gender.values()[gender];
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setCaloriesPerDay(int caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
    }
}
