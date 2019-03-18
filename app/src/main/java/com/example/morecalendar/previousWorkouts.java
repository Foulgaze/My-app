package com.example.morecalendar;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class previousWorkouts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_workouts);
        Button returnBtn = findViewById(R.id.returnBtn);
        final ListView previousView = findViewById(R.id.previousView);
        String theDate = getIntent().getStringExtra("THE_DATE");
        String theName = getIntent().getStringExtra("WORKOUT_TITLE");
        final Databasehelper mDatabaserHelper = new Databasehelper(previousWorkouts.this);
        ArrayList<String> displayList = new ArrayList<>();
        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<String> finalList = new ArrayList<>();
        Map<Integer, ArrayList<String>> orderedMap = new HashMap<>();
        Cursor data = mDatabaserHelper.getListContents();
        data.moveToFirst();
        toastMessage(theName);
        while(!data.isAfterLast()){
            if(data.getString(1).equals(theName)){
                displayList.add(data.getString(1) + "\t\tWeight: " + data.getDouble(2) + "\t\tReps: " + data.getDouble(3) + " (" + data.getString(4) + ")");
                dateList.add(String.valueOf(parseDate(data.getString(4))));
                toastMessage("Hello");
            }
            data.moveToNext();
        }
        for(int i =0; i < dateList.size(); i++){
            if(orderedMap.containsKey(Integer.parseInt(dateList.get(i)))){
                orderedMap.get(Integer.parseInt(dateList.get(i))).add(displayList.get(i));
            }else{
                ArrayList<String> display = new ArrayList<>();
                display.add(displayList.get(i));
                orderedMap.put(Integer.parseInt(dateList.get(i)), display);

            }
        }
        Object[] keys = orderedMap.keySet().toArray();
        Arrays.sort(keys);
        for(Object key : keys){
            finalList.addAll(orderedMap.get(key));
        }
        ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, finalList);
        previousView.setAdapter(listAdapter);
//        toastMessage(Arrays.toString(displayList.toArray()));


        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBack = new Intent(previousWorkouts.this, specific_date.class);
                goBack.putExtra("THE_DATE", getIntent().getStringExtra("THE_DATE"));
                startActivity(goBack);
            }
        });



    }
    public static Integer parseDate(String s){
        String[] numArr = s.split("/");
        return Integer.parseInt(numArr[0]) + (Integer.parseInt(numArr[1]) * 34) + Integer.parseInt(numArr[2]);
    }
    public void toastMessage(String message){
        Toast.makeText(previousWorkouts.this, message, Toast.LENGTH_LONG).show();
    }
}
