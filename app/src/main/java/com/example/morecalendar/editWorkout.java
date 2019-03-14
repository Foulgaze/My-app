package com.example.morecalendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.morecalendar.Databasehelper;
import com.example.morecalendar.MainActivity;
import com.example.morecalendar.R;
import com.example.morecalendar.specific_date;

public class editWorkout extends AppCompatActivity {
    private static final String TAG = "editWorkout";
    private int selectedID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);
        ImageButton increaseBtn = (ImageButton) findViewById(R.id.increaseBtn);
        ImageButton increaseBtn2 = (ImageButton) findViewById(R.id.increaseBtn2);
        ImageButton decreaseBtn = (ImageButton) findViewById(R.id.decreaseBtn);
        ImageButton decreaseBtn2 = (ImageButton) findViewById(R.id.decreaseBtn2);
        Button saveBtn = (Button) findViewById(R.id.saveBtn);
        Button deleteBtn = (Button) findViewById(R.id.deleteBtn);
        final EditText weightView = findViewById(R.id.weightView);
        final EditText repView = findViewById(R.id.repView);
        final EditText changedWorkout = (EditText) findViewById(R.id.changedWorkout);
        final Databasehelper myDB = new Databasehelper(this);
        final String selectedName = getIntent().getStringExtra("name");
        selectedID = getIntent().getIntExtra("id", -1); // -1 is default
        weightView.setText(getIntent().getStringExtra("weight"));
        repView.setText(getIntent().getStringExtra("reps"));
        changedWorkout.setText(selectedName, TextView.BufferType.EDITABLE);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = changedWorkout.getText().toString();

                if(!item.equals("")){
                   myDB.updateWorkout(item, selectedID, selectedName, weightView.getText().toString(), repView.getText().toString());
                    Intent goBack = new Intent(editWorkout.this, MainActivity.class);
                    startActivity(goBack);
//                    Intent goback = getIntent();
//                    finish();
//                    startActivity(goback);
                }else{
                    toastMessage("You must enter a name");
                }
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB.deleteWorkout(selectedID, selectedName);
                toastMessage("Removed from database");
                Intent goBack = new Intent(editWorkout.this, MainActivity.class);
                startActivity(goBack);
            }
        });
        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightView.setText(String.valueOf(Double.parseDouble(weightView.getText().toString()) + 2.5));
            }
        });
        increaseBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repView.setText(String.valueOf(Double.parseDouble(repView.getText().toString()) + 1));
            }
        });
        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Double.parseDouble(weightView.getText().toString()) - 2.5 > 0){
                    weightView.setText(String.valueOf(Double.parseDouble(weightView.getText().toString()) - 2.5));
                } else{
                    weightView.setText("0");
                }
            }
        });
        decreaseBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Double.parseDouble(repView.getText().toString()) - 1 > 0){
                    repView.setText(String.valueOf(Double.parseDouble(repView.getText().toString()) - 1));
                } else{
                    repView.setText("0");
                }

            }
        });
    }



    public void toastMessage(String message){
        Toast.makeText(editWorkout.this, message, Toast.LENGTH_LONG).show();
    }

}
