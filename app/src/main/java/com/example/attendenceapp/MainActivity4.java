package com.example.attendenceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity4 extends AppCompatActivity {
RecyclerView recyclerView;  Toolbar toolbar;
    ArrayList<Company> companies =new ArrayList<>();
    TextView title,sub; ImageButton imageButton;
    CompanyAdapter adapter;DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        dbHelper =new DbHelper(this);
       try {
            loadData();
        }catch (NullPointerException e){
           e.printStackTrace();
       }
        recyclerView = findViewById(R.id.rec);

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
       try {
            companies = (ArrayList<Company>) getIntent().getExtras().getSerializable("list");
        }catch (NullPointerException e){
           e.printStackTrace();
       }


//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("data",new JSONArray(companies));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String string =jsonObject.toString();

        adapter =new CompanyAdapter(this,companies);
        recyclerView.setAdapter(adapter);
       adapter.notifyDataSetChanged();

        SharedPreferences sharedPreferences = getSharedPreferences("rec", MODE_PRIVATE);
        Gson gson = new Gson();String json = sharedPreferences.getString("task", null);
        Type type = new TypeToken<ArrayList<Company>>() {}.getType();companies = gson.fromJson(json, type);
        if (companies== null) { companies = new ArrayList<>(); }
        adapter =new CompanyAdapter(MainActivity4.this,companies);
//        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        toolbar=findViewById(R.id.tool);
        title=toolbar.findViewById(R.id.tt); title.setVisibility(View.GONE);
        sub=toolbar.findViewById(R.id.tt2); sub.setVisibility(View.GONE);
        imageButton=toolbar.findViewById(R.id.back);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
        ImageButton imageButton1=toolbar.findViewById(R.id.save);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh=getApplicationContext().getSharedPreferences("rec", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sh.edit();
                Gson gson = new Gson();
                String json = gson.toJson(companies);editor.putString("task", json);editor.apply();
                Toast.makeText(getApplicationContext(),"Records Saved ...",Toast.LENGTH_SHORT).show();
//                Gson gson =new Gson();
//                String str=gson.toJson(companies);
//                long r_id= dbHelper.addRec(str);
            }
        });

    }

    private void loadData()  {
//        Cursor cursor =dbHelper.getRecTable();
//        companies.clear();
//        while (cursor.moveToNext()){
////            int id=cursor.getInt(cursor.getColumnIndex(DbHelper.r_id));
//
//            String className=cursor.getString(cursor.getColumnIndex(DbHelper.REC_COMPANY));
////            String sub=cursor.getString(cursor.getColumnIndex(DbHelper.CLASS_SUBJECT_KEY));
////            JSONObject jsonObject = new JSONObject(className);
////            JSONArray items= jsonObject.optJSONArray("data");
//            Gson gson = new Gson();
//            Type type = new TypeToken<ArrayList<Company>>() {}.getType();
//            ArrayList<Company> arrayList =gson.fromJson(className,type);
//            adapter =new CompanyAdapter(MainActivity4.this,arrayList);
//        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//
//        }
        SharedPreferences sharedPreferences = getSharedPreferences("rec", MODE_PRIVATE);
        Gson gson = new Gson();String json = sharedPreferences.getString("task", null);
        Type type = new TypeToken<ArrayList<Company>>() {}.getType();companies = gson.fromJson(json, type);
        if (companies== null) { companies = new ArrayList<>(); }
        adapter =new CompanyAdapter(MainActivity4.this,companies);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}