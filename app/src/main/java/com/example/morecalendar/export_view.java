package com.example.morecalendar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class export_view extends AppCompatActivity {
    Databasehelper mDatabaseHelper;
    String theName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_view);
        mDatabaseHelper = new Databasehelper(this);
        Button exportAllBtn = findViewById(R.id.exportAllBtn);
        Button selectExportBtn = findViewById(R.id.selectExportBtn);
        final Spinner workoutChoice = findViewById(R.id.workoutChoice);
        ImageButton calendarBtn = findViewById(R.id.calendarBtn);
        final ArrayList<String> nameList = new ArrayList<>();
        Cursor names = mDatabaseHelper.getListContents();
        names.moveToFirst();
        while(!names.isAfterLast()){
            nameList.add(names.getString(1));
            names.moveToNext();
        }
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(nameList);
        nameList.clear();
        nameList.addAll(hashSet);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item, nameList);
        workoutChoice.setAdapter(spinnerArrayAdapter);
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calendar = new Intent(export_view.this, MainActivity.class);
                startActivity(calendar);
            }
        });
        exportAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    maxExport("allWorkouts");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        workoutChoice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                theName = nameList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                toastMessage("D:");
            }
        });
        selectExportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(workoutChoice.getCount() == 0){
                    toastMessage("You Must Select A Workout To Export");
                } else{
                    try {
                        selectedExport("selectedExport");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    public void maxExport(String workoutFile) throws IOException {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "Workouts");
        if (!file.mkdirs()) {
            toastMessage("Directory not Created, Check Permissions");
        }
        File graph = new File(file + "/" + workoutFile + ".CSV");

        FileOutputStream fileOutputStream = new FileOutputStream(graph);
        Cursor data = mDatabaseHelper.getListContents();
        String[] columns = data.getColumnNames();

        fileOutputStream.write(columns[4].getBytes());
        fileOutputStream.write(",".getBytes());
        fileOutputStream.write(columns[1].getBytes());
        fileOutputStream.write(",".getBytes());
        fileOutputStream.write(columns[2].getBytes());
        fileOutputStream.write(",".getBytes());
        fileOutputStream.write(columns[3].getBytes());
        fileOutputStream.write(",".getBytes());

        fileOutputStream.write("\n".getBytes());
        data.moveToFirst();
        ArrayList<String> displayList = new ArrayList<>();
        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<String> finalList = new ArrayList<>();
        Map<Integer, ArrayList<String>> orderedMap = new HashMap<>();
        while(!data.isAfterLast()){
            displayList.add(data.getString(4) + "," + data.getString(1) + ","+ data.getDouble(2) + "," + data.getDouble(3));
            dateList.add(String.valueOf(parseDate(data.getString(4))));
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
        for(int i = 0; i < finalList.size(); i++){
            fileOutputStream.write(finalList.get(i).getBytes());
            fileOutputStream.write("\n".getBytes());
        }
        fileOutputStream.close();
        if(graph.exists()){
            toastMessage("Successfully Exported Workouts");
        } else{
            toastMessage("Failed Export, Check File Permissions");
        }
    }
    public void selectedExport(String workoutFile) throws IOException {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "Workouts");
        if (!file.mkdirs()) {
            toastMessage("Directory not Created, Check Permissions");
        }
        File graph = new File(file + "/" +workoutFile + ".CSV");
        FileOutputStream fileOutputStream = new FileOutputStream(graph);
        Cursor data = mDatabaseHelper.getListContents();
        String[] columns = data.getColumnNames();

        fileOutputStream.write(columns[4].getBytes());
        fileOutputStream.write(",".getBytes());
        fileOutputStream.write(columns[1].getBytes());
        fileOutputStream.write(",".getBytes());
        fileOutputStream.write(columns[2].getBytes());
        fileOutputStream.write(",".getBytes());
        fileOutputStream.write(columns[3].getBytes());
        fileOutputStream.write(",".getBytes());

        fileOutputStream.write("\n".getBytes());
        data.moveToFirst();
        ArrayList<String> displayList = new ArrayList<>();
        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<String> finalList = new ArrayList<>();
        Map<Integer, ArrayList<String>> orderedMap = new HashMap<>();
        while(!data.isAfterLast()){
            if(data.getString(1).equals(theName)){
                displayList.add(data.getString(4) + "," + data.getString(1) + ","+ data.getDouble(2) + "," + data.getDouble(3));
                dateList.add(String.valueOf(parseDate(data.getString(4))));
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
        for(int i = 0; i < finalList.size(); i++){
            fileOutputStream.write(finalList.get(i).getBytes());
            fileOutputStream.write("\n".getBytes());
        }
        fileOutputStream.close();
        if(graph.exists()){
            toastMessage("Successfully Exported Workouts");
        } else{
            toastMessage("Failed Export, Check File Permissions");
        }

    }
    public static Integer parseDate(String s){
        String[] numArr = s.split("/");
        return Integer.parseInt(numArr[0]) + (Integer.parseInt(numArr[1]) * 34) + Integer.parseInt(numArr[2]);
    }
    public void toastMessage(String message){
        Toast.makeText(export_view.this, message, Toast.LENGTH_LONG).show();
    }
}
