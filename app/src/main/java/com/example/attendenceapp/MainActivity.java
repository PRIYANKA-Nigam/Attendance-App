package com.example.attendenceapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;

import at.markushi.ui.CircleButton;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;FloatingActionButton fl; RecyclerView recyclerView;
    CustomAdapter customAdapter;ArrayList<ClassItem> arrayList=new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    @Override protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); loadData();
        fl=(FloatingActionButton)findViewById(R.id.fab);
        fl.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) { showDialogs(); }});
        recyclerView=(RecyclerView)findViewById(R.id.rec);recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        customAdapter=new CustomAdapter(this,arrayList);recyclerView.setAdapter(customAdapter);
        customAdapter.setOnItemClickListener(position -> gotoItemActivity(position));setToolbar();
        customAdapter.setOnItemCLongclickListener(pos->DeleteData(pos)); setToolbar();
    }

    private boolean DeleteData(int pos) {
        new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Are You Sure?")
                .setMessage("Do You Want to delete this Data").setPositiveButton("Yes", new DialogInterface.OnClickListener() {@Override
        public void onClick(DialogInterface dialog, int which) {
            arrayList.remove(pos);customAdapter.notifyDataSetChanged();
            SharedPreferences sh=getApplicationContext().getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sh.edit();Gson gson = new Gson();
            String json = gson.toJson(arrayList);editor.putString("task list", json);editor.apply();
            Toast.makeText(getApplicationContext(),"Data Deleted ...",Toast.LENGTH_SHORT).show();
        }
        }).setNegativeButton("No",null).show();
      return true;
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<ClassItem>>() {}.getType();arrayList = gson.fromJson(json, type);
        if (arrayList == null) { arrayList = new ArrayList<>(); } }
    private void setToolbar() {
        toolbar=(Toolbar)findViewById(R.id.tool);TextView title=toolbar.findViewById(R.id.tt);
        TextView sub=toolbar.findViewById(R.id.tt2);
        ImageButton imageButton=toolbar.findViewById(R.id.back);
        ImageButton imageButton1=toolbar.findViewById(R.id.save);
        title.setText("Attendance App");sub.setVisibility(View.GONE);
        imageButton.setVisibility(View.INVISIBLE);imageButton1.setVisibility(View.INVISIBLE); }
        private void gotoItemActivity(int position) {
        Intent intent=new Intent(this,MainActivity2.class);
    intent.putExtra("fname",arrayList.get(position).getFname());
    intent.putExtra("lname",arrayList.get(position).getLname());
    intent.putExtra("pos",position);startActivity(intent); }
    private void showDialogs() {
      MyDialog myDialog=new MyDialog();myDialog.show(getSupportFragmentManager(),MyDialog.class_dialog);
      myDialog.setListener((fname,lname)->addClass(fname,lname)); }
    private void addClass(String fname,String lname) {
        arrayList.add(new ClassItem(fname,lname)); customAdapter.notifyDataSetChanged();
     saveData();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();Gson gson = new Gson();
        String json = gson.toJson(arrayList);editor.putString("task list", json);editor.apply();
        Toast.makeText(getApplicationContext(),"Data Saved ...",Toast.LENGTH_SHORT).show();
    }
}