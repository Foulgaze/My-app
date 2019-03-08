package com.example.morecalendar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import static java.util.logging.Logger.global;

public class MainActivity extends AppCompatActivity {
    Databasehelper mDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        final TextView date = (TextView) findViewById(R.id.date);
        final int count = 0;
        mDatabaseHelper = new Databasehelper(this);
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
    }

}