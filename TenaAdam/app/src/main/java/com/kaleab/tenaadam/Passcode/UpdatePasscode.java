package com.kaleab.tenaadam.Passcode;

import android.Manifest;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
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
import com.kaleab.tenaadam.Data.MotherDbHelper;
import com.kaleab.tenaadam.HomePage.MainActivity;
import com.kaleab.tenaadam.R;
import com.kaleab.tenaadam.Walkthrough.RegisterActivity;

public class UpdatePasscode extends AppCompatActivity {

    EditText new_password, verify_new_password, new_secrete_answer;
    Spinner new_secrete_question_spinner;
    Button update_password;
    Switch enabe_password_switch;
    LinearLayout password_field;


    MotherDbHelper motherDbHelper = new MotherDbHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_passcode);

        enabe_password_switch = (Switch) findViewById(R.id.enable_update_password_switch);
        password_field = (LinearLayout) findViewById(R.id.enable_update_password_field);
        new_password = (EditText) findViewById(R.id.new_password);
        verify_new_password = (EditText) findViewById(R.id.verify_new_password);
        new_secrete_question_spinner = (Spinner) findViewById(R.id.new_secrete_question);
        new_secrete_answer = (EditText) findViewById(R.id.new_secrete_answer);


        update_password = (Button) findViewById(R.id.update_password_button);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.secrete_questions_spinner,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        new_secrete_question_spinner.setAdapter(adapter);

        RegisterActivity.is_password_enabled = true; //default
        password_enable_status();
    }

    private void password_enable_status(){
        enabe_password_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    password_field.setVisibility(View.VISIBLE);
                    RegisterActivity.is_password_enabled = true;
                }else{
                    new_password.setText("");
                    verify_new_password.setText("");
                    new_secrete_answer.setText("");
                    RegisterActivity.is_password_enabled = false;
                    password_field.setVisibility(View.GONE);
                }
            }
        });
    }

    public void updatePassword(View v){

        SQLiteDatabase db = motherDbHelper.getWritableDatabase();
        //if is password is enabled
        if(RegisterActivity.is_password_enabled){

            Toast.makeText(this, "Password is enabled ", Toast.LENGTH_LONG).show();
            if(checkValidity()){
                String password = new_password.getText().toString();
                String secrete_question = new_secrete_question_spinner.getSelectedItem().toString();
                String secrete_answer = new_secrete_answer.getText().toString();
                int success = CRUD.updatePassword(db, password, secrete_question, secrete_answer);
                if(success == 1){
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Log.e("UpdatePasscode", "Error updating password");
                }
                //goto main avtivity
            }
        }else{
            int success = CRUD.updatePassword(db, null, null, null);
            if(success == 1){
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
            }else{
                Log.e("UpdatePassword", "Error udpatting password");
            }
        }

    }

    private boolean checkValidity(){
        if(new_password.getText().toString().equals("") || new_password.getText().toString() == null){
            //it should not be empty
            //new_password.setText("");
            new_password.setHint("password is required");
            new_password.setHintTextColor(Color.parseColor("red"));
            //Log.d("Empty password", "password not filled");
            return false;
        }else if(new_password.getText().toString().length() < 4 || new_password.getText().toString().length() > 4){
            //it should be 4 digit length
            new_password.setText("");
            new_password.setHint("the password should be 4 digit");
            new_password.setHintTextColor(Color.parseColor("red"));
            return false;
        }
        if(verify_new_password.getText().toString().equals("") || verify_new_password.getText().toString() == null){
            //should be verified
            verify_new_password.setText("");
            verify_new_password.setHintTextColor(Color.parseColor("red"));
            verify_new_password.setHint("Verify the password please");
            return false;
        }else if(!verify_new_password.getText().toString().equals(new_password.getText().toString())){
            verify_new_password.setText("");
            verify_new_password.setHintTextColor(Color.parseColor("red"));
            verify_new_password.setHint("password does not match");
            return false;
        }
        if(new_secrete_answer.getText().toString().equals("") || new_secrete_answer.getText().toString() == null){
            new_secrete_answer.setText("");
            new_secrete_answer.setHintTextColor(Color.parseColor("red"));
            new_secrete_answer.setHint("you should answer the secrete question");
            return false;
        }

        return true;
    }
}
