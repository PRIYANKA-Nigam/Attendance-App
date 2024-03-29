package com.example.attendenceapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MainActivity2 extends AppCompatActivity {
Toolbar toolbar;private String f,l;private  int p; private RecyclerView recyclerView;
private StudentsAdapter adapter; private RecyclerView.LayoutManager layoutManager;
DbHelper dbHelper; private long cid;
  private MyCalendar calendar;
  private TextView sub; File filePdf;
private ArrayList<StudentItem> arrayList=new ArrayList<StudentItem>(); @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); setContentView(R.layout.activity_main2);
//    loadData();
        Intent intent=getIntent(); f=intent.getStringExtra("fname");
        l=intent.getStringExtra("lname"); p=intent.getIntExtra("pos",-1);
        cid=intent.getLongExtra("cid",-1);
        StorageManager storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
        StorageVolume storageVolume = storageManager.getStorageVolumes().get(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            filePdf = new File(storageVolume.getDirectory().getPath()+"/Download/"+f+".pdf");
        }
        calendar=new MyCalendar();
    dbHelper=new DbHelper(this);

       setToolbar();
        try  {
            loadStuData();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        recyclerView=(RecyclerView)findViewById(R.id.rec2); recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this); recyclerView.setLayoutManager(layoutManager);
        adapter=new StudentsAdapter(this,arrayList); recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> changeStatus(position));
//         adapter.setOnItemLongClickListener(pos->DeleteData(pos));

      // loadData();
        try  {
            loadStuData();
            loadStatusData();
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
                String roll = cursor.getString(cursor.getColumnIndex(DbHelper.STUDENT_ROLL_KEY));
                String name = cursor.getString(cursor.getColumnIndex(DbHelper.STUDENT_NAME_KEY));
                arrayList.add(new StudentItem(name, roll, sid));
            }
        }
        cursor.close();
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
        @RequiresApi(api = Build.VERSION_CODES.N)
        private void setToolbar() { toolbar=(Toolbar)findViewById(R.id.tool);TextView title=toolbar.findViewById(R.id.tt);
       sub=toolbar.findViewById(R.id.tt2);
        ImageButton imageButton=toolbar.findViewById(R.id.back);
        ImageButton imageButton1=toolbar.findViewById(R.id.save);title.setText(f);
            sub.setText(l+"  |   "+ calendar.getDate());
            imageButton.setOnClickListener(v -> onBackPressed());
             imageButton1.setVisibility(View.INVISIBLE);
         toolbar.inflateMenu(R.menu.stuent_menu);
         toolbar.setOnMenuItemClickListener(menuItem-> {
             try {
                 return onMenuItemClick(menuItem);
             } catch (IOException e) {
                 e.printStackTrace();
             }
             return false;
         }); }



    private void saveStatus() {
    for (StudentItem studentItem : arrayList){
        String status=studentItem.getStatus();
        if (status!="P")   //.....................not working
            status = "A";

        long value=dbHelper.addStatus(studentItem.getS_id(),cid,calendar.getDate(),status);
        if (value==-1)
            dbHelper.updateStatus(studentItem.getS_id(),calendar.getDate(),status);
         Toast.makeText(getApplicationContext(),"Status Saved..,",Toast.LENGTH_SHORT).show();

    }
    }
private void loadStatusData(){ //....................... not working
    for (StudentItem studentItem : arrayList){
        String status= dbHelper.getStatus(studentItem.getS_id(),calendar.getDate());
        if (status!=null)
            studentItem.setStatus(status);
        else
            studentItem.setStatus(" ");
    }
    adapter.notifyDataSetChanged();
}

//    private Boolean DeleteData(int pos) {
//    dbHelper.deleteStudent(arrayList.get(pos).getS_id());
//  arrayList.remove(pos);adapter.notifyDataSetChanged();
////  SharedPreferences sh=getApplicationContext().getSharedPreferences("shared", Context.MODE_PRIVATE);
////  SharedPreferences.Editor editor = sh.edit();Gson gson = new Gson();
////  String json = gson.toJson(arrayList);editor.putString("task", json);editor.apply();
////  Toast.makeText(getApplicationContext(),"Data Deleted ...",Toast.LENGTH_SHORT).show();
//  return true;
// }

 @RequiresApi(api = Build.VERSION_CODES.N)
 private boolean onMenuItemClick(MenuItem menuItem) throws IOException {
        if (menuItem.getItemId()==R.id.addStu){ showAddStudentDialog(); }
        if (menuItem.getItemId()==R.id.addDate){
            showCalendar();
        }
        if (menuItem.getItemId()==R.id.showAtt){
//           openSheetList()
            PdfDocument pdfDocument =  new PdfDocument();
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(recyclerView.getWidth(),recyclerView.getHeight(),1).create();
            PdfDocument.Page page = pdfDocument.startPage(pageInfo);
            recyclerView.draw(page.getCanvas());
            pdfDocument.finishPage(page);
            pdfDocument.writeTo(new FileOutputStream(filePdf));
            Toast.makeText(getApplicationContext(),"Pdf Downloaded ...",Toast.LENGTH_SHORT).show();
            pdfDocument.close();
        }
     if (menuItem.getItemId()==R.id.showAtts){
//       Intent intent=new Intent(Intent.ACTION_VIEW, Uri.fromFile(filePdf));
//       intent.setType("application/pdf");  //USING these lines i'm able to view my pdf but have to
                                  //explicitly nevigate to the path in the local.SO ,used below one
//       this.startActivity(intent);
         StorageManager storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
         StorageVolume storageVolume = storageManager.getStorageVolumes().get(0);
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
             filePdf = new File(storageVolume.getDirectory().getPath()+"/Download/"+f+".pdf");
             Uri uri = FileProvider.getUriForFile(getApplicationContext(),"com.example.attendenceapp"+".provider",filePdf);
             Intent intent=new Intent(Intent.ACTION_VIEW);  //with this i can directly view my pdf
       intent.setDataAndType(uri,"application/pdf");
       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_GRANT_READ_URI_PERMISSION);
       startActivity(intent);
         }
     }
        return true; }

    private void openSheetList() {
    Intent intent =new Intent(this,SheetListActivity.class);
    intent.putExtra("cid",cid);
    startActivity(intent);
    }

    private void showCalendar() {
    calendar.show(getSupportFragmentManager(),"");
    calendar.setOnCalendarOKClickListener((this::onCalendarOKClicked));
    }
    private void onCalendarOKClicked(int year,int month,int day){
    calendar.setDate(year,month,day);
        sub.setText(l+"  |   "+ calendar.getDate());
        loadStatusData();
    }

    private void showAddStudentDialog() { MyDialog dialog=new MyDialog();
        dialog.show(getSupportFragmentManager(),MyDialog.student_dialog);
        dialog.setListener((roll,name)->addStudent(roll,name)); }
        private void addStudent(String S_roll, String name) {
    String roll=(S_roll);
     long sid=dbHelper.addStudent(cid,name,roll);
     StudentItem studentItem =new StudentItem(name,roll,sid);
      arrayList.add(studentItem);
        adapter.notifyDataSetChanged();
//         saveData();
}

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()){
           case 0 :
               deleteStudent(item.getGroupId());
               break;
           case 1:
               editProfile(item.getGroupId());
               break;
       }
        return super.onContextItemSelected(item);
    }

    private void editProfile(int position) {
        Intent intent =new Intent(getApplicationContext(),MainActivity3.class);
        intent.putExtra("id",String.valueOf(arrayList.get(position).getRoll()));
        intent.putExtra("name",arrayList.get(position).getName());
        startActivity(intent);
    }

    private void deleteStudent(int position) {
        dbHelper.deleteStudent(arrayList.get(position).getS_id());
        arrayList.remove(position);adapter.notifyDataSetChanged();
    }
}