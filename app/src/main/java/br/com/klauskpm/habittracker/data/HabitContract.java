package br.com.klauskpm.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by Kazlauskas on 12/11/2016.
 */

public final class HabitContract {
    private HabitContract() {}

    public static class HabitEntry implements BaseColumns {
        public static final String TABLE_NAME = "habits";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_HABIT_TITLE = "title";
        public static final String COLUMN_HABIT_DAY = "day";

        public static final int DAY_SUNDAY = 0;
        public static final int DAY_MONDAY = 1;
        public static final int DAY_TUESDAY = 2;
        public static final int DAY_WEDNESDAY = 3;
        public static final int DAY_THURSDAY = 4;
        public static final int DAY_FRIDAY = 5;
        public static final int DAY_SATURDAY = 6;
    }
}
