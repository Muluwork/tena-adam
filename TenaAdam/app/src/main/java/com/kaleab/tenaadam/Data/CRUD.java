package com.kaleab.tenaadam.Data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.kaleab.tenaadam.Passcode.ResetPasscode;

import static com.kaleab.tenaadam.R.id.secrete_question;

/**
 * Created by Kal on 3/29/2018.
 */

public class CRUD {

    public static long saveMotherProfile(SQLiteDatabase db, String name, String baby_nick_name, String week_pregnant, String pass_code,String secrete_question,
                                         String secrete_answer){

        ContentValues values = new ContentValues();

        values.put(MotherContract.Mother.COLUMN_NAME, name);
        values.put(MotherContract.Mother.COLUMN_NICKNAME, baby_nick_name);
        values.put(MotherContract.Mother.COLUMN_WEEKS_PREGNANT, week_pregnant);
        values.put(MotherContract.Mother.COLUMN_PASS_CODE, pass_code);
        values.put(MotherContract.Mother.COLUMN_SECRETE_QUESTION, secrete_question);
        values.put(MotherContract.Mother.COLUMN_SECRETE_ANSWER, secrete_answer);

        return db.insert(MotherContract.Mother.TABLE_NAME, null, values);
    }

    public static String getPassCode(SQLiteDatabase db){
        //db writable data
        Cursor cursor = db.rawQuery("SELECT * FROM " + MotherContract.Mother.TABLE_NAME, null);
        StringBuffer buffer = new StringBuffer();
        if(cursor.getCount() == 0){
            return null;
        }

        while(cursor.moveToNext()){
            buffer.append(cursor.getString(cursor.getColumnIndex(MotherContract.Mother.COLUMN_PASS_CODE)));
        }
        return buffer.toString();
    }
    public static String getMotherName(SQLiteDatabase db){
        Cursor cursor = db.rawQuery("SELECT * FROM " + MotherContract.Mother.TABLE_NAME, null);
        String buffer = new String();

        if(cursor.getCount() == 0) return null;
        while(cursor.moveToNext()){
            buffer = cursor.getString(cursor.getColumnIndex(MotherContract.Mother.COLUMN_NAME));
        }

        return buffer;
    }
    public static String[] getSecreteQandA(SQLiteDatabase db){
        Cursor cursor = db.rawQuery("SELECT * FROM " + MotherContract.Mother.TABLE_NAME, null);
        if(cursor.getCount() == 0) return null;
        String[] result = new String[2];
        cursor.moveToLast();

        result[0] = cursor.getString(cursor.getColumnIndex(MotherContract.Mother.COLUMN_SECRETE_QUESTION));
        result[1] = cursor.getString(cursor.getColumnIndex(MotherContract.Mother.COLUMN_SECRETE_ANSWER));

        String[] kal = {"Kaleab", "Mekonen"};
        return result;
        //return result;
    }
    public static int updatePassword(SQLiteDatabase db, String password, String question, String answer){
        ContentValues values = new ContentValues();
        values.put(MotherContract.Mother.COLUMN_PASS_CODE, password);
        values.put(MotherContract.Mother.COLUMN_SECRETE_QUESTION, question);
        values.put(MotherContract.Mother.COLUMN_SECRETE_ANSWER, answer);
        int success = db.update(MotherContract.Mother.TABLE_NAME, values, MotherContract.Mother._ID + " = ? ", new String[] {"1"});
        return success;
    }

    public static String getAllUsers(SQLiteDatabase db, String table_name, String col_name){
        Cursor cursor = db.rawQuery("SELECT * FROM " + table_name, null);
        StringBuilder result = new StringBuilder();

        while(cursor.moveToNext()){
            String user = cursor.getString(cursor.getColumnIndex(col_name));
            result.append(user + "\n\n");
        }
        return result.toString();
    }
    public static void fillDailyTip(SQLiteDatabase db, int day_number, String daily_tip){
        ContentValues values = new ContentValues();
        values.put(MotherContract.DailyTip.COLUMN_DAY_NUMBER, day_number);
        values.put(MotherContract.DailyTip.COLUMN_TIP, daily_tip);
        db.insert(MotherContract.DailyTip.TABLE_NAME, null, values);
    }
}