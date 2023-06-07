package com.example.attendenceapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ViewProfileActivity extends AppCompatActivity {
    Toolbar toolbar; TextView title,sub;
File filePdf; String data;
String[] s; int k=0;
String name="",id="";
ArrayAdapter<String> arrayAdapter;
ListView listView;
ArrayList<String> arrayList;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
       try {
            loadData();
        }catch (NullPointerException e){
           e.printStackTrace();
       }
        arrayList = new ArrayList<>();
        try{
            data = MainActivity3.getActivityInstance().getData();
            String[] p=data.split(",");
            name=p[0];
            id=p[1];
            arrayList.add(name);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();


       listView = findViewById(R.id.ll);
        SharedPreferences sh=getSharedPreferences("list",MODE_PRIVATE);
        Set<String> set=sh.getStringSet("notes",new HashSet<String>());
        for(String i:set){
            arrayList.add(i);
            arrayAdapter  =new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arrayList);
            listView.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StorageManager storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
                StorageVolume storageVolume = storageManager.getStorageVolumes().get(0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    filePdf = new File(storageVolume.getDirectory().getPath()+"/Download/"+arrayList.get(position)+".pdf");
                    Uri uri = FileProvider.getUriForFile(getApplicationContext(),"com.example.attendenceapp"+".provider",filePdf);
                    Intent intent=new Intent(Intent.ACTION_VIEW);  //with this i can directly view my pdf
                    intent.setDataAndType(uri,"application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);
                }
            }
        });


        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() { @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.print:
                    bottomNavigationView.setVisibility(View.VISIBLE);
                    Intent intent= new Intent(getApplicationContext(), MainActivity3.class);
                    intent.putExtra("name",name);
                    intent.putExtra("id",id);
                    startActivity(intent);
                    Toast.makeText(ViewProfileActivity.this,"Print as PDF...",Toast.LENGTH_LONG).show();
                    overridePendingTransition(0,0);
                    return true;
                case R.id.view:
                    return true;
            }
            return false; }});
        setToolbar();

    }

    private void setToolbar() {
        toolbar=(Toolbar)findViewById(R.id.tool);
        title=toolbar.findViewById(R.id.tt);
        sub=toolbar.findViewById(R.id.tt2);
        sub.setVisibility(View.INVISIBLE);
        title.setText("View Profiles");
        ImageButton imageButton=toolbar.findViewById(R.id.back);
        imageButton.setOnClickListener(v -> onBackPressed());
        ImageButton imageButton1=toolbar.findViewById(R.id.save);
        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sh=getApplicationContext().getSharedPreferences("list", Context.MODE_PRIVATE);
                HashSet<String> set=new HashSet<>(arrayList);sh.edit().putStringSet("notes",set).apply();
            }
        });
    }

    private void loadData() {
        SharedPreferences sh=getSharedPreferences("list",MODE_PRIVATE);
        Set<String> set=sh.getStringSet("notes",new HashSet<String>());
        for(String i:set){
            arrayList.add(i);
            arrayAdapter  =new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arrayList);
            listView.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();
        }
    }


}