package com.example.morecalendar;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class add_workout extends AppCompatActivity {
    Databasehelper mDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);
        ImageButton increaseBtn = (ImageButton) findViewById(R.id.increaseBtn);
        ImageButton increaseBtn2 = (ImageButton) findViewById(R.id.increaseBtn2);
        ImageButton decreaseBtn = (ImageButton) findViewById(R.id.decreaseBtn);
        ImageButton decreaseBtn2 = (ImageButton) findViewById(R.id.decreaseBtn2);
        final EditText weightView = (EditText) findViewById(R.id.weightView);
        final EditText repView = (EditText) findViewById(R.id.repView);

        Button saveBtn = (Button) findViewById(R.id.saveBtn);
        final TextView workoutName = (TextView) findViewById(R.id.workoutName);
        mDatabaseHelper = new Databasehelper(this);
        final String theDate = getIntent().getStringExtra("THE_DATE");
        if(getIntent().hasExtra("COPY")){
            Cursor copyWorkout = mDatabaseHelper.getListContents();
            copyWorkout.moveToLast();
            weightView.setText(copyWorkout.getString(2));
            repView.setText(copyWorkout.getString(3));
            workoutName.setText(copyWorkout.getString(1));
        } else{
            weightView.setText("0");
            repView.setText("0");
        }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(String.valueOf(weightView.getText()).equals("")){
                    toastMessage("Don't forget to put a weight amount");
                }
                else if(String.valueOf(repView.getText()).equals("")){
                    toastMessage("Don't forget to put a rep count");
                } else{
                    String actualName;
                    if(workoutName.getText().toString().equals("")){
                        actualName = "Workout";
                    }else{
                        actualName = workoutName.getText().toString();
                    }
                    AddData(actualName, Double.parseDouble(weightView.getText().toString()),Double.parseDouble(repView.getText().toString()), theDate );
                    Intent savePage = new Intent(add_workout.this, specific_date.class);
                    savePage.putExtra("THE_DATE", theDate);
                    startActivity(savePage);
                }
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
    public void AddData(String newEntry, Double weight, Double reps, String date){
        boolean insertData = mDatabaseHelper.addData(newEntry, weight, reps, date);
        System.out.print(insertData);
    }
    public void toastMessage(String message){
        Toast.makeText(add_workout.this, message, Toast.LENGTH_LONG).show();
    }
}
