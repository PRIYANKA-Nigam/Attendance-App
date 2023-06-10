package com.example.attendenceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class AttendanceActivity extends AppCompatActivity {
    Toolbar toolbar;private String f,l;private  int p; private RecyclerView recyclerView;
    private StudentsAdapter2 adapter; private RecyclerView.LayoutManager layoutManager;
    DbHelper dbHelper; private long cid;
    private MyCalendar calendar;
    private TextView sub;
    private ArrayList<StudentItem> arrayList=new ArrayList<StudentItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        calendar=new MyCalendar();
        dbHelper=new DbHelper(this);

        setToolbar();
        recyclerView=(RecyclerView)findViewById(R.id.rec2); recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this); recyclerView.setLayoutManager(layoutManager);
        adapter=new StudentsAdapter2(this,arrayList); recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> changeStatus(position));
        adapter.setOnItemLongClickListener(pos->DeleteData(pos));

        // loadData();
        try  {
            loadStuData();
            loadStatusData();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void loadStuData() {
        Cursor cursor;
        cursor =dbHelper.getStudentTable(cid);
        arrayList.clear();
        if (cursor!=null) {
            while (cursor.moveToNext()) {
                long sid = cursor.getLong(cursor.getColumnIndex(DbHelper.s_id));
                String roll = cursor.getString(cursor.getColumnIndex(DbHelper.STUDENT_ROLL_KEY));
                String name = cursor.getString(cursor.getColumnIndex(DbHelper.STUDENT_NAME_KEY));
                arrayList.add(new StudentItem(name, roll, sid));
            }
        }
        cursor.close();
    }

    private boolean DeleteData(int pos) {
        dbHelper.deleteStudent(arrayList.get(pos).getS_id());
        arrayList.remove(pos);adapter.notifyDataSetChanged();
        return true;
    }

    private void changeStatus(int position) {
        String status=arrayList.get(position).getStatus();
        if (status.equals("P"))
            status="A";
        else
            status="P";
        arrayList.get(position).setStatus(status);
        adapter.notifyItemChanged(position);

    }
    private void setToolbar() {
        toolbar=(Toolbar)findViewById(R.id.tool);TextView title=toolbar.findViewById(R.id.tt);
        sub=toolbar.findViewById(R.id.tt2);
        ImageButton imageButton=toolbar.findViewById(R.id.back);
        ImageButton imageButton1=toolbar.findViewById(R.id.save);title.setText(f);
        sub.setText(l+"  |   "+ calendar.getDate());
        imageButton.setOnClickListener(v -> onBackPressed());
        imageButton1.setVisibility(View.INVISIBLE);
        toolbar.inflateMenu(R.menu.stuent_menu);
        toolbar.setOnMenuItemClickListener(menuItem-> {
            return onMenuItemClick(menuItem);

        });
    }

    private boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId()==R.id.addStu){ showAddStudentDialog(); }
        if (menuItem.getItemId()==R.id.addDate){
            showCalendar();
        }
        return true;
    }
    private void onCalendarOKClicked(int year,int month,int day){
        calendar.setDate(year,month,day);
        sub.setText(l+"  |   "+ calendar.getDate());
        loadStatusData();
    }

    private void loadStatusData() {
        for (StudentItem studentItem : arrayList){
            String status= dbHelper.getStatus(studentItem.getS_id(),calendar.getDate());
            if (status!=null)
                studentItem.setStatus(status);
        else
            studentItem.setStatus("A");
        }
        adapter.notifyDataSetChanged();
    }

    private void showCalendar() {
        calendar.show(getSupportFragmentManager(),"");
        calendar.setOnCalendarOKClickListener((this::onCalendarOKClicked));
    }

    private void showAddStudentDialog() {
        MyDialog dialog=new MyDialog();
        dialog.show(getSupportFragmentManager(),MyDialog.student_dialog);
        dialog.setListener((roll,name)->addStudent(roll,name));
    }

    private void addStudent(String roll, String name) {
        long sid=dbHelper.addStudent(cid,name,roll);
        StudentItem studentItem =new StudentItem(name,roll,sid);
        arrayList.add(studentItem);
        adapter.notifyDataSetChanged();
    }
}