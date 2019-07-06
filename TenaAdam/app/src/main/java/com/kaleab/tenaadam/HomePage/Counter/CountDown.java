package com.kaleab.tenaadam.HomePage.Counter;

import android.os.CountDownTimer;

/**
 * Created by Kaleab on 4/18/2018.
 */

public class CountDown extends CountDownTimer {

    public CountDown(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {

    }

    @Override
    public void onFinish() {

    }
}
