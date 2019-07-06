package com.kaleab.tenaadam.Walkthrough;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kal on 4/2/2018.
 */

public class WalkthroughManager  {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    public WalkthroughManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences("first", 0);
        editor = pref.edit();
    }

    public void isFirst(boolean isFirst){
        editor.putBoolean("check", isFirst);
        editor.apply();
    }

    public boolean Check() {
        return pref.getBoolean("check", true);
    }
}
