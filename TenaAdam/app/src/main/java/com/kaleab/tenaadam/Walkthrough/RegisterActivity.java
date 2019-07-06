package com.kaleab.tenaadam.Walkthrough;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.kaleab.tenaadam.Data.CRUD;
import com.kaleab.tenaadam.Data.MotherContract;
import com.kaleab.tenaadam.Data.MotherDbHelper;
import com.kaleab.tenaadam.Passcode.PasscodeActivity;
import com.kaleab.tenaadam.R;


public class RegisterActivity extends AppCompatActivity {

    MotherDbHelper motherDbHelper = new MotherDbHelper(this);

    Switch enable_password_switch;
    LinearLayout enable_password_field;
    Button register_button;

    public static boolean is_password_enabled = false;

    //fields
    EditText mother_name;
    EditText baby_nick_name;
    EditText week_pregnant;
    EditText password;
    EditText verify_password;
    Spinner secrete_question_spinner;
    EditText secrete_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        enable_password_switch = (Switch) findViewById(R.id.enable_password_switch);
        enable_password_field = (LinearLayout) findViewById(R.id.enable_password_field);
        register_button = (Button) findViewById(R.id.register_button);
        mother_name = (EditText) findViewById(R.id.mother_name);
        baby_nick_name = (EditText) findViewById(R.id.baby_nick_name);
        week_pregnant = (EditText) findViewById(R.id.week_pregnant);
        password = (EditText) findViewById(R.id.password);
        verify_password = (EditText) findViewById(R.id.verify_password);
        secrete_question_spinner = (Spinner) findViewById(R.id.secrete_question);
        secrete_answer = (EditText) findViewById(R.id.secrete_answer);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.secrete_questions_spinner,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        secrete_question_spinner.setAdapter(adapter);

        password_enable_status();
        register();
    }


    private void password_enable_status(){
        enable_password_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    enable_password_field.setVisibility(View.VISIBLE);
                    is_password_enabled = true;
                }else{
                    password.setText("");
                    verify_password.setText("");
                    secrete_answer.setText("");
                    is_password_enabled = false;
                    enable_password_field.setVisibility(View.GONE);
                }
            }
        });
    }

    private void register(){
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_entry_valid()){
                    long check = saveMotherProfile();
                    if(check > 0){
                        Toast.makeText(getBaseContext(), Long.toString(check), Toast.LENGTH_LONG).show();
                        Intent i = new Intent(RegisterActivity.this, PasscodeActivity.class);
                        startActivity(i);
                        finish();
                    }
                }else{
                    Toast.makeText(getBaseContext(), "Fill every field", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean is_entry_valid(){

        if(mother_name.getText().toString().equals("") || mother_name.getText().toString() == null){
            mother_name.setHint("your name is required");
            mother_name.setHintTextColor(Color.parseColor("red"));
            return false;
        }
        if(baby_nick_name.getText().toString().equals("") || baby_nick_name.getText().toString() == null){
            baby_nick_name.setHint("your baby's nick name is required");
            baby_nick_name.setHintTextColor(Color.parseColor("red"));
            return false;
        }
        if(week_pregnant.getText().toString().equals("") || week_pregnant.getText().toString() == null){
            week_pregnant.setHintTextColor(Color.parseColor("red"));
            week_pregnant.setHint("week's pregnant is required");
            //it should not be empty
            return false;
        }else if(Integer.parseInt(week_pregnant.getText().toString()) < 1 ||
                Integer.parseInt(week_pregnant.getText().toString()) > 48){
            //it should be between 1 and 48
            week_pregnant.setText("");
            week_pregnant.setHintTextColor(Color.parseColor("red"));
            week_pregnant.setHint("number between 1 and 48");
            return false;
        }

        if(is_password_enabled){
            if(password.getText().toString().equals("") || password.getText().toString() == null){
                //it should not be empty
                password.setText("");
                password.setHint("password is required");
                password.setHintTextColor(Color.parseColor("red"));
                Log.d("Empty password", "password not filled");
                return false;
            }else if(password.getText().toString().length() < 4 || password.getText().toString().length() > 4){
                //it should be 4 digit length
                password.setText("");
                password.setHint("the password should be 4 digit");
                password.setHintTextColor(Color.parseColor("red"));
                return false;
            }
            if(verify_password.getText().toString().equals("") || verify_password.getText().toString() == null){
                //should be verified
                verify_password.setText("");
                verify_password.setHintTextColor(Color.parseColor("red"));
                verify_password.setHint("Verify the password please");
                return false;
            }else if(!verify_password.getText().toString().equals(password.getText().toString())){
                verify_password.setText("");
                verify_password.setHintTextColor(Color.parseColor("red"));
                verify_password.setHint("password does not match");
                return false;
            }
            if(secrete_answer.getText().toString().equals("") || secrete_answer.getText().toString() == null){
                secrete_answer.setText("");
                secrete_answer.setHintTextColor(Color.parseColor("red"));
                secrete_answer.setHint("you should answer the secrete question");
                return false;
            }
        }

        return true;
    }

    private void displayDbInfo(){

        String[] projection = {
                MotherContract.Mother.COLUMN_PASS_CODE
        };
        Cursor cursor = getContentResolver().query(MotherContract.Mother.CONTENT_URI, projection, null, null, null);
        String passcode = cursor.getString(cursor.getColumnIndex(MotherContract.Mother.COLUMN_PASS_CODE));
        Toast.makeText(this, passcode, Toast.LENGTH_LONG).show();
    }

    private long saveMotherProfile(){

        SQLiteDatabase db  = motherDbHelper.getWritableDatabase();

        String mother_name = this.mother_name.getText().toString();
        String baby_nick_name = this.baby_nick_name.getText().toString();
        String week_pregnant = this.week_pregnant.getText().toString();
        String password = this.password.getText().toString();
        String secrete_question = this.secrete_question_spinner.getSelectedItem().toString();
        String secrete_answer = this.secrete_answer.getText().toString();

        return CRUD.saveMotherProfile(db, mother_name, baby_nick_name, week_pregnant,
                password, secrete_question, secrete_answer);
    }

}