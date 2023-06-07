package com.example.attendenceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {
RecyclerView recyclerView;
    ArrayList<Company> companies =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        recyclerView = findViewById(R.id.rec);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        companies = (ArrayList<Company>) getIntent().getExtras().getSerializable("list");
//        String name = getIntent().getStringExtra("Empname");

        recyclerView.setAdapter(new CompanyAdapter(this,companies));

    }
}