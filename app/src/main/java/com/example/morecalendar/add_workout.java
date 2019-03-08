package com.example.morecalendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class add_workout extends AppCompatActivity {
    Databasehelper mdDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_workout);
        ImageButton increaseBtn = (ImageButton) findViewById(R.id.increaseBtn);
        ImageButton increaseBtn2 = (ImageButton) findViewById(R.id.increaseBtn2);
        ImageButton decreaseBtn = (ImageButton) findViewById(R.id.decreaseBtn);
        ImageButton decreaseBtn2 = (ImageButton) findViewById(R.id.decreaseBtn2);
        final EditText weightView = (EditText) findViewById(R.id.weightView);
        weightView.setText("0");
        final EditText repView = (EditText) findViewById(R.id.repView);
        repView.setText("0");
        Button saveBtn = (Button) findViewById(R.id.saveBtn);
        final TextView workoutName = (TextView) findViewById(R.id.workoutName);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddData(workoutName.getText().toString());
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
    public void AddData(String newEntry){
        boolean insertData = mdDatabaseHelper.addData(newEntry);
        System.out.print(insertData);
    }
}
