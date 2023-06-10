package com.example.attendenceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TodoActivity extends AppCompatActivity {
ListView listView;
ArrayList<String> arrayList=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
       try {
            loadTask();
        }catch (NullPointerException e){
           e.printStackTrace();
       }
        dbHelper =new DbHelper(this);
        listView = findViewById(R.id.ll);
        arrayList = (ArrayList<String>) getIntent().getSerializableExtra("task");
//        String task = getIntent().getStringExtra("task");
//        arrayList.add(task);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
    }

    private void loadTask() {
        Cursor cursor =dbHelper.getTask();
        arrayList.clear();   //...................... not working
        while (cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex("id"));
            String className=cursor.getString(cursor.getColumnIndex("name"));
            arrayList.add(className);
        }
    }
}