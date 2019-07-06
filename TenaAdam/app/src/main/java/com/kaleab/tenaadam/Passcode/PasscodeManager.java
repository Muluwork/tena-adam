package com.kaleab.tenaadam.Passcode;

import android.content.Context;
import android.content.SharedPreferences;

import com.kaleab.tenaadam.Walkthrough.RegisterActivity;

/**
 * Created by Kaleab on 4/18/2018.
 */

public class PasscodeManager {


    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    public PasscodeManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences("passcode_active", 0);
        editor = pref.edit();
    }

    public void check_password_enable(){

        boolean exists = RegisterActivity.is_password_enabled;

        editor.putBoolean("check", exists);

        editor.apply();
    }

    public boolean Check() {
        return pref.getBoolean("check", true);
    }
}
