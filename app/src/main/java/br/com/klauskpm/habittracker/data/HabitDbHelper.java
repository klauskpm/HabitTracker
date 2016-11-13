package br.com.klauskpm.habittracker.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import br.com.klauskpm.habittracker.data.HabitContract.HabitEntry;

/**
 * Created by Kazlauskas on 12/11/2016.
 */
public class HabitDbHelper extends SQLiteOpenHelper {
    /**
     * The constant DATABASE_VERSION.
     */
    public static final int DATABASE_VERSION = 1;

    /**
     * The constant DATABASE_NAME.
     */
    public static final String DATABASE_NAME = "habit_tracker.db";

    /**
     * The constant DEFAULT_PROJECTIONS.
     */
    public static final String[] DEFAULT_PROJECTIONS = {
            HabitEntry._ID,
            HabitEntry.COLUMN_HABIT_TITLE,
            HabitEntry.COLUMN_HABIT_DAY
    };

    /**
     * Instantiates a new Habit db helper.
     *
     * @param context the context
     */
    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE_HABITS = "CREATE TABLE " + HabitEntry.TABLE_NAME + " ("
                + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitEntry.COLUMN_HABIT_TITLE + " TEXT NOT NULL, "
                + HabitEntry.COLUMN_HABIT_DAY + " INTEGER"
                + ");";

        db.execSQL(SQL_CREATE_TABLE_HABITS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Insert habit
     *
     * @param titleString the title
     * @param dayInteger  the day
     * @return the row ID
     */
    public long insertHabit(String titleString, int dayInteger) {
        SQLiteDatabase writableDb = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_TITLE, titleString);
        values.put(HabitEntry.COLUMN_HABIT_DAY, dayInteger);

        return writableDb.insert(HabitEntry.TABLE_NAME, null, values);
    }

    /**
     * Insert dummy habits.
     */
    public void insertDummyHabits() {
        ArrayList<String> titles = new ArrayList<String>();
        Integer[] days = {
                HabitEntry.DAY_SUNDAY,
                HabitEntry.DAY_MONDAY,
                HabitEntry.DAY_TUESDAY,
                HabitEntry.DAY_WEDNESDAY,
                HabitEntry.DAY_THURSDAY,
                HabitEntry.DAY_FRIDAY,
                HabitEntry.DAY_SATURDAY
        };

        titles.add("Go to gym");
        titles.add("Wake up early");
        titles.add("Arrive early at work");
        titles.add("Do at least one good deed");

        for (int i = 0; i < titles.size(); i++) {
            int randomNumber = (new Random()).nextInt(days.length - 1);
            int day = days[randomNumber];

            long newRowId = insertHabit(titles.get(i), day);
            if (newRowId == -1) {
                Log.e("Habit", "insertDummyHabits: Error with saving pet");
            } else {
                Log.d("Habit", "insertDummyHabits: Pet saved with row id:" + newRowId);
            }
        }
    }

    /**
     * Find and read the habits
     *
     * @param projection    fields to return
     * @param selection     fields to filter
     * @param selectionArgs values for filters
     * @return the habits as string
     */
    public String readHabit(String[] projection, String selection, String[] selectionArgs) {
        SQLiteDatabase readableDb = this.getReadableDatabase();

        if (projection == null) {
            projection = DEFAULT_PROJECTIONS;
        }

        Cursor cursor = readableDb.query(
                HabitEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
        int titleColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_TITLE);
        int dayColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_DAY);

        StringBuilder stringBuilder = new StringBuilder();

        while (cursor.moveToNext()) {
            int currentId = cursor.getInt(idColumnIndex);
            String currentTitle = cursor.getString(titleColumnIndex);
            int currentDay = cursor.getInt(dayColumnIndex);

            stringBuilder.append("\n" +
                    currentId + " - " +
                    currentTitle + " - " +
                    currentDay);
        }

        cursor.close();
        Log.d("Habit", "readHabit: " + stringBuilder.toString());
        return stringBuilder.toString();
    }

    /**
     * Find and read the habit by ID
     *
     * @param id the habit ID
     * @return the habit
     */
    public String readHabitById(int id) {
        String selection = HabitEntry._ID + "=?";
        String[] selecionArgs = { "" + id };

        return this.readHabit(null, selection, selecionArgs);
    }
}
