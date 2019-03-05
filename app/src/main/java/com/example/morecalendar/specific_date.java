package com.example.morecalendar;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;

public class specific_date extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_date);

        String theDate = getIntent().getStringExtra("THE_DATE");
        TextView chosenDate = (TextView) findViewById(R.id.chosenDate);
        chosenDate.setText(theDate);
        File file = new File(Environment.getExternalStorageDirectory(), "hello");

        Button addExercise = (Button) findViewById(R.id.addExercize);
        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent workoutPage = new Intent(specific_date.this, add_workout.class);
                startActivity(workoutPage);
            }
        });
    }
}
