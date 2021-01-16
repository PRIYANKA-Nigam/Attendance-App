package com.example.attendenceapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

import java.util.ArrayList;

import at.markushi.ui.CircleButton;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;FloatingActionButton fl; RecyclerView recyclerView;
    CustomAdapter customAdapter;ArrayList<ClassItem> arrayList=new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    @Override protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);fl=(FloatingActionButton)findViewById(R.id.fab);
        fl.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) { showDialogs(); }});
        recyclerView=(RecyclerView)findViewById(R.id.rec);recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        customAdapter=new CustomAdapter(this,arrayList);recyclerView.setAdapter(customAdapter);
        customAdapter.setOnItemClickListener(position -> gotoItemActivity(position));setToolbar(); }
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
        arrayList.add(new ClassItem(fname,lname)); customAdapter.notifyDataSetChanged(); }}