package com.example.morecalendar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.logging.Logger.global;

public class MainActivity extends AppCompatActivity {
    Databasehelper mDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalendarView calendarView = findViewById(R.id.calendarView);
        final TextView date = findViewById(R.id.date);
        Button exportBtn = findViewById(R.id.exportBtn);
        final int count = 0;
        mDatabaseHelper = new Databasehelper(this);
//        mDatabaseHelper.delete("people_table");
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date.setText(month + "/" + dayOfMonth + "/" + year);
                String theDate = month + "/" + dayOfMonth + "/" + year;
                Intent chosenDay = new Intent(MainActivity.this, specific_date.class);
                chosenDay.putExtra("THE_DATE", theDate);
                startActivity(chosenDay);
                System.out.println(count);
            }
        });
        exportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor datacheck = mDatabaseHelper.getListContents();
                if(datacheck.getCount() > 0){
                    Intent exportData = new Intent(MainActivity.this, export_view.class);
                    startActivity(exportData);
                } else{
                    toastMessage("There Are No Workouts to Export");
                }

            }
        });

    }
    public void toastMessage(String message){
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }


}
