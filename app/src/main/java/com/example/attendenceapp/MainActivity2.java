package com.example.attendenceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
Toolbar toolbar;private String f,l;private  int p; private RecyclerView recyclerView;
private StudentsAdapter adapter; private RecyclerView.LayoutManager layoutManager;
private ArrayList<StudentItem> arrayList=new ArrayList<StudentItem>(); @Override
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); setContentView(R.layout.activity_main2);
        Intent intent=getIntent(); f=intent.getStringExtra("fname");
       l=intent.getStringExtra("lname"); p=intent.getIntExtra("pos",-1); setToolbar();
        recyclerView=(RecyclerView)findViewById(R.id.rec2); recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this); recyclerView.setLayoutManager(layoutManager);
        adapter=new StudentsAdapter(this,arrayList); recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> changeStatus(position)); }
        private void changeStatus(int position) { String status=arrayList.get(position).getStatus();
        if (status.equals("P"))  status="A";
        else status="P";
        arrayList.get(position).setStatus(status); adapter.notifyItemChanged(position); }
        private void setToolbar() { toolbar=(Toolbar)findViewById(R.id.tool);TextView title=toolbar.findViewById(R.id.tt);
        TextView sub=toolbar.findViewById(R.id.tt2); ImageButton imageButton=toolbar.findViewById(R.id.back);
        ImageButton imageButton1=toolbar.findViewById(R.id.save);title.setText(f);sub.setText(l);
        imageButton.setOnClickListener(v -> onBackPressed());
         toolbar.inflateMenu(R.menu.stuent_menu);
         toolbar.setOnMenuItemClickListener(menuItem->onMenuItemClick(menuItem)); }
         private boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId()==R.id.addStu){ showAddStudentDialog(); }
        return true; }
        private void showAddStudentDialog() { MyDialog dialog=new MyDialog();
        dialog.show(getSupportFragmentManager(),MyDialog.student_dialog);
        dialog.setListener((roll,name)->addStudent(roll,name)); }
        private void addStudent(String roll, String name) { arrayList.add(new StudentItem(roll, name));
        adapter.notifyDataSetChanged(); }}