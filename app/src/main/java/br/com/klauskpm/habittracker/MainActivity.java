package br.com.klauskpm.habittracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.klauskpm.habittracker.data.HabitDbHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HabitDbHelper dbHelper = new HabitDbHelper(this);
        // Insert dummy habits
        dbHelper.insertDummyHabits();

        // Read all habits
        dbHelper.readHabit(null, null, null);

        // Read only one habit
        dbHelper.readHabitById(3);
    }
}
