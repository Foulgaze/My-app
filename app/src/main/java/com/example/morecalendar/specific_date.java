package com.example.morecalendar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class specific_date extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_date);

        final String theDate = getIntent().getStringExtra("THE_DATE");
        TextView chosenDate = (TextView) findViewById(R.id.chosenDate);
        chosenDate.setText(theDate);
        Databasehelper myDB = new Databasehelper(this);
        ListView listView = (ListView) findViewById(R.id.listView);
        Cursor data = myDB.getListContents();
        Button backBtn = (Button) findViewById(R.id.backBtn);
        ArrayList<String> nameList = new ArrayList<String>();
        System.out.println(data.getCount());
        if(data.getCount() == 0){
            Toast.makeText(specific_date.this, "The database was empty", Toast.LENGTH_LONG).show();
        } else{
            data.moveToFirst();
            while(!data.isAfterLast()){
                String date = data.getString(4);
                if(date.equals(theDate)){
                    nameList.add(data.getString(1) + "\t\tWeight: " + data.getDouble(2) + "\t\tReps: " + data.getDouble(3));
                }
                data.moveToNext();
            }
            ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, nameList);
            listView.setAdapter(listAdapter);
        }

        Button addExercise = (Button) findViewById(R.id.addExercize);
        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent workoutPage = new Intent(specific_date.this, add_workout.class);
                workoutPage.putExtra("THE_DATE", theDate);
                startActivity(workoutPage);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(specific_date.this, MainActivity.class);
                startActivity(goBack);
            }
        });
        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeData = new Intent(specific_date.this, editWorkout.class);
            }
        });
    }
}
