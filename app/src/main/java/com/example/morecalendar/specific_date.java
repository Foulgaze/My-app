package com.example.morecalendar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
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
    private static final String TAG = "Specific_Date";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_date);
        final String theDate = getIntent().getStringExtra("THE_DATE");
        TextView chosenDate = (TextView) findViewById(R.id.chosenDate);
        chosenDate.setText(theDate);
        final Databasehelper myDB = new Databasehelper(this);
        ListView listView = (ListView) findViewById(R.id.listView);
        Cursor data = myDB.getListContents();
        Button backBtn = (Button) findViewById(R.id.backBtn);
        final ArrayList<String> nameList = new ArrayList<String>();
        final ArrayList<String> weightList = new ArrayList<>();
        final ArrayList<String> repList = new ArrayList<>();
        final ArrayList<String> referenceList = new ArrayList<>();
        final ArrayList<String> idList = new ArrayList<>();
        System.out.println(data.getCount());
        if(data.getCount() == 0){
            toastMessage("The database was empty");
        } else{
            data.moveToFirst();
            while(!data.isAfterLast()){
                String date = data.getString(4);
                if(date.equals(theDate)){
                    nameList.add(data.getString(1) + "\t\tWeight: " + data.getDouble(2) + "\t\tReps: " + data.getDouble(3));
                    referenceList.add(data.getString(1));
                    weightList.add(data.getString(2));
                    repList.add(data.getString(3));
                    idList.add(data.getString(0));
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String name = parent.getItemAtPosition(position).toString();
//                name = name.substring(0, name.indexOf('\t'));
                String name = referenceList.get(position);
                Log.d(TAG, "onItemClick: The ID is: " + name);
                Cursor data = myDB.getItemID(name);

//                int itemID = -1;
                int itemID = Integer.parseInt(idList.get(position));
//                while(data.moveToNext()){
//                    itemID = data.getInt(0);
//                }
                if(itemID > -1){
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent editScreenIntent = new Intent(specific_date.this, editWorkout.class);
                    editScreenIntent.putExtra("id", itemID);
                    editScreenIntent.putExtra("name", name);
                    editScreenIntent.putExtra("weight", weightList.get(position));
                    editScreenIntent.putExtra("reps", repList.get(position));
                    startActivity(editScreenIntent);
                }
                else{
                    toastMessage("No ID associated with that name: " + name);
                }

            }
        });

        }
        public void toastMessage(String message){
            Toast.makeText(specific_date.this, message, Toast.LENGTH_LONG).show();
        }
    }

