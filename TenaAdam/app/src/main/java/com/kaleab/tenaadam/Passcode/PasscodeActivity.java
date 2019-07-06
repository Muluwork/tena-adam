package com.kaleab.tenaadam.Passcode;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaleab.tenaadam.Data.CRUD;
import com.kaleab.tenaadam.Data.MotherDbHelper;
import com.kaleab.tenaadam.HomePage.MainActivity;
import com.kaleab.tenaadam.R;

public class PasscodeActivity extends AppCompatActivity {

    MotherDbHelper motherDbHelper = new MotherDbHelper(this);

    TextView pass_code, forget_pass_code;
    Button no1, no2, no3, no4, no5, no6, no7, no8, no9, no0;
    ImageView back_space, done;
    PasscodeManager passcodeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        passcodeManager = new PasscodeManager(this);


        passcodeManager.check_password_enable();
        //for now it is enabled

        if(!passcodeManager.Check()){
            Intent i = new Intent(PasscodeActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        setContentView(R.layout.activity_passcode);

        pass_code = (TextView) findViewById(R.id.pass_code);

        no1 = (Button) findViewById(R.id.no_1);
        no2 = (Button) findViewById(R.id.no_2);
        no3 = (Button) findViewById(R.id.no_3);
        no4 = (Button) findViewById(R.id.no_4);
        no5 = (Button) findViewById(R.id.no_5);
        no6 = (Button) findViewById(R.id.no_6);
        no7 = (Button) findViewById(R.id.no_7);
        no8 = (Button) findViewById(R.id.no_8);
        no9 = (Button) findViewById(R.id.no_9);
        no0 = (Button) findViewById(R.id.no_0);
        back_space = (ImageView) findViewById(R.id.back_space);
        done = (ImageView) findViewById(R.id.done);

        forget_pass_code = (TextView) findViewById(R.id.forget_password);

        numberPad(no1, "1");
        numberPad(no2, "2");
        numberPad(no3, "3");
        numberPad(no4, "4");
        numberPad(no5, "5");
        numberPad(no6, "6");
        numberPad(no7, "7");
        numberPad(no8, "8");
        numberPad(no9, "9");
        numberPad(no0, "0");


        back_space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Passcode", "CLICKED");
                //delete the last number entered
                StringBuilder pass_code_buffer = new StringBuilder();

                String string_pass_code = pass_code.getText().toString();
                if (string_pass_code.length() > 0) {
                    for(int i = 0; i < string_pass_code.length() - 1; i++){
                        pass_code_buffer.append(string_pass_code.toCharArray()[i]);
                    }
                }
                pass_code.setText(pass_code_buffer);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pass_code.getText().toString().length() == 4){
                    //Toast.makeText(PasscodeActivity.this, "Length is correct", Toast.LENGTH_SHORT).show();
                    SQLiteDatabase db = motherDbHelper.getReadableDatabase();
                    String original_passcode = CRUD.getPassCode(db);
                    if(original_passcode.equals(pass_code.getText().toString())){
                        Intent i = new Intent(PasscodeActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }else {
                        pass_code.setText("");
                        Toast.makeText(PasscodeActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                        forget_pass_code.setVisibility(View.VISIBLE);
                    }
                }else {
                    pass_code.setText("");
                    Toast.makeText(PasscodeActivity.this, "The pass code should be 4 digit length", Toast.LENGTH_SHORT).show();
                    forget_pass_code.setVisibility(View.VISIBLE);
                }
            }
        });

        forget_pass_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO reset pass_code page
                Intent i = new Intent(PasscodeActivity.this, ResetPasscode.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void numberPad(Button button, final String stringNumber){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pass_code.getText().toString().length() < 4) pass_code.append(stringNumber);
                else{
                    Toast.makeText(PasscodeActivity.this, "Can not be more than 4 digit", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
