package com.example.attendenceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
Toolbar toolbar;private String f,l;private  int p; private RecyclerView recyclerView;
private StudentsAdapter adapter; private RecyclerView.LayoutManager layoutManager;
DbHelper dbHelper; private long cid;
  private MyCalendar calendar=new MyCalendar();
  private TextView sub;
private ArrayList<StudentItem> arrayList=new ArrayList<StudentItem>(); @Override
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); setContentView(R.layout.activity_main2);
//    loadData();

    dbHelper=new DbHelper(this);
        Intent intent=getIntent(); f=intent.getStringExtra("fname");
       l=intent.getStringExtra("lname"); p=intent.getIntExtra("pos",-1);
       cid=intent.getLongExtra("cid",-1);

       setToolbar();
        recyclerView=(RecyclerView)findViewById(R.id.rec2); recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this); recyclerView.setLayoutManager(layoutManager);
        adapter=new StudentsAdapter(this,arrayList); recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> changeStatus(position));
         adapter.setOnItemLongClickListener(pos->DeleteData(pos));
     //   loadStatusData();
      // loadData();
        try  {
            loadStuData();

        }catch (NullPointerException e){
            e.printStackTrace();
        }

}

    private void loadStuData() {  Cursor cursor;
      cursor =dbHelper.getStudentTable(cid);
        arrayList.clear();
        if (cursor!=null) {
            while (cursor.moveToNext()) {
                long sid = cursor.getLong(cursor.getColumnIndex(DbHelper.s_id));
                int roll = cursor.getInt(cursor.getColumnIndex(DbHelper.STUDENT_ROLL_KEY));
                String name = cursor.getString(cursor.getColumnIndex(DbHelper.STUDENT_NAME_KEY));
                arrayList.add(new StudentItem(name, roll, sid));
            }
        }
        cursor.close();
    }

    private void changeStatus(int position) { String status=arrayList.get(position).getStatus();
        if (status.equals("P"))  status="A";
        else status="P";
        arrayList.get(position).setStatus(status); adapter.notifyItemChanged(position); }
        private void setToolbar() { toolbar=(Toolbar)findViewById(R.id.tool);TextView title=toolbar.findViewById(R.id.tt);
        TextView  sub=toolbar.findViewById(R.id.tt2);
        ImageButton imageButton=toolbar.findViewById(R.id.back);
        ImageButton imageButton1=toolbar.findViewById(R.id.save);title.setText(f);
            sub.setText(l+"  |   "+ calendar.getDate());
            imageButton.setOnClickListener(v -> onBackPressed());
        imageButton1.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
         saveStatus();
           //  saveData();
         }
        });
         toolbar.inflateMenu(R.menu.stuent_menu);
         toolbar.setOnMenuItemClickListener(menuItem->onMenuItemClick(menuItem)); }

    private void saveStatus() {
    for (StudentItem studentItem : arrayList){
        String status=studentItem.getStatus();
        if (status=="P")
            status="A";
        long value=dbHelper.addStatus(studentItem.getS_id(),status);
        Toast.makeText(getApplicationContext(),"Status saved",Toast.LENGTH_SHORT).show();
    }
    }
public void loadStatusData(){
    for (StudentItem studentItem : arrayList){
        String status= dbHelper.getStatus(studentItem.getS_id());
        if (status==null)
            studentItem.setStatus(status);
    }
    adapter.notifyDataSetChanged();
}

    private Boolean DeleteData(int pos) {
    dbHelper.deleteStudent(arrayList.get(pos).getS_id());
  arrayList.remove(pos);adapter.notifyDataSetChanged();
//  SharedPreferences sh=getApplicationContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
//  SharedPreferences.Editor editor = sh.edit();Gson gson = new Gson();
//  String json = gson.toJson(arrayList);editor.putString("task", json);editor.apply();
//  Toast.makeText(getApplicationContext(),"Data Deleted ...",Toast.LENGTH_SHORT).show();
  return true;
 }

 private boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId()==R.id.addStu){ showAddStudentDialog(); }
        if (menuItem.getItemId()==R.id.addDate){
            showCalendar();
        }
        return true; }

    private void showCalendar() {
    calendar.show(getSupportFragmentManager(),"");
    calendar.setOnCalendarOKClickListener((this::onCalendarOKClicked));
    }
    private void onCalendarOKClicked(int year,int month,int day){
    calendar.setDate(year,month,day);
        sub.setText(l+"  |   "+ calendar.getDate());
    }

    private void showAddStudentDialog() { MyDialog dialog=new MyDialog();
        dialog.show(getSupportFragmentManager(),MyDialog.student_dialog);
        dialog.setListener((roll,name)->addStudent(roll,name)); }
        private void addStudent(String S_roll, String name) {
    int roll=Integer.parseInt(S_roll);
     long sid=dbHelper.addStudent(cid,name,roll);
     StudentItem studentItem =new StudentItem(name,roll,sid);
      arrayList.add(studentItem);
        adapter.notifyDataSetChanged();
//         saveData();
}

 private void saveData() {
  SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
  SharedPreferences.Editor editor = sharedPreferences.edit();
  Gson gson = new Gson();
  String json = gson.toJson(arrayList);editor.putString("task", json);editor.apply();
  Toast.makeText(getApplicationContext(),"Data Saved ...",Toast.LENGTH_SHORT).show();
 }
 private void loadData() {
  SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
  Gson gson = new Gson();String json = sharedPreferences.getString("task", null);
  Type type = new TypeToken<ArrayList<StudentItem>>() {}.getType();arrayList = gson.fromJson(json, type);
  if (arrayList == null) { arrayList = new ArrayList<>(); } }
}