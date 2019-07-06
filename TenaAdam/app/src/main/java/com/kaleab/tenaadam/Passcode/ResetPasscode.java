package com.kaleab.tenaadam.Passcode;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kaleab.tenaadam.Data.CRUD;
import com.kaleab.tenaadam.Data.MotherContract;
import com.kaleab.tenaadam.Data.MotherDbHelper;
import com.kaleab.tenaadam.R;

public class ResetPasscode extends AppCompatActivity {
    Spinner security_secrete_question_spinner;
    Button security_button;
    EditText security_question_edit_text;

    MotherDbHelper motherDbHelper = new MotherDbHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_passcode);

        security_secrete_question_spinner = (Spinner) findViewById(R.id.security_secrete_question);
        security_button = (Button) findViewById(R.id.security_button);
        security_question_edit_text = (EditText) findViewById(R.id.security_secrete_answer);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.secrete_questions_spinner,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        security_secrete_question_spinner.setAdapter(adapter);

    }

    public void nextStep(View v){
        SQLiteDatabase db = motherDbHelper.getReadableDatabase();
        String[] qAndA = CRUD.getSecreteQandA(db);

        String secrete_question = security_secrete_question_spinner.getSelectedItem().toString();
        String secrete_answer = security_question_edit_text.getText().toString();

        if(qAndA[0].equals(secrete_question) && qAndA[1].equals(secrete_answer)){
            Intent i = new Intent(this, UpdatePasscode.class);
            startActivity(i);
            finish();
        }else{
            security_question_edit_text.setText("");
            security_question_edit_text.setHint("Answer does not match");
        }
    }
}
