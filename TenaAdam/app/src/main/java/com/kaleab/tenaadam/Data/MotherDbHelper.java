package com.kaleab.tenaadam.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kaleab on 5/22/2018.
 */

public class MotherDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mother.db";
    private static final int DATABASE_VERSION = 1;

    public MotherDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_mother_table = "CREATE TABLE " + MotherContract.Mother.TABLE_NAME + "("
                + MotherContract.Mother._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MotherContract.Mother.COLUMN_NAME + " TEXT NOT NULL, "
                + MotherContract.Mother.COLUMN_NICKNAME + " TEXT NOT NULL, "
                + MotherContract.Mother.COLUMN_PASS_CODE + " INTEGER, "
                + MotherContract.Mother.COLUMN_SECRETE_QUESTION + " TEXT, "
                + MotherContract.Mother.COLUMN_SECRETE_ANSWER + " TEXT, "
                + MotherContract.Mother.COLUMN_WEEKS_PREGNANT + " INTEGER);";

        String create_daily_tip_table = "CREATE TABLE " + MotherContract.DailyTip.TABLE_NAME + "("
                + MotherContract.DailyTip._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MotherContract.DailyTip.COLUMN_DAY_NUMBER + " INTEGER, "
                + MotherContract.DailyTip.COLUMN_TIP + " TEXT);";

        //TODO create other tables here
        db.execSQL(create_mother_table);
        db.execSQL(create_daily_tip_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MotherContract.Mother.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MotherContract.Note.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MotherContract.BabyStatus.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MotherContract.Reminder.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MotherContract.Picture.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MotherContract.WeeklyTip.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MotherContract.DailyTip.TABLE_NAME);
        onCreate(db);
    }
}
