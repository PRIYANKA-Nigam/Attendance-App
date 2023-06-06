package com.example.attendenceapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;

import at.markushi.ui.CircleButton;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;FloatingActionButton fl; RecyclerView recyclerView;
    CustomAdapter customAdapter;ArrayList<ClassItem> arrayList=new ArrayList<>();
    RecyclerView.LayoutManager layoutManager; DbHelper dbHelper;
    File filePdf;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        loadData();
        toolbar=(Toolbar)findViewById(R.id.tool);
        toolbar.inflateMenu(R.menu.pdf_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.d){
//           openSheetList()
                    PdfDocument pdfDocument =  new PdfDocument();
                    PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(recyclerView.getWidth(),recyclerView.getHeight(),1).create();
                    PdfDocument.Page page = pdfDocument.startPage(pageInfo);
                    recyclerView.draw(page.getCanvas());
                    pdfDocument.finishPage(page);
                    try {
                        pdfDocument.writeTo(new FileOutputStream(filePdf));
                        Toast.makeText(getApplicationContext(),"Pdf Downloaded ...",Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    pdfDocument.close();
                }
                if (item.getItemId()==R.id.d2){
                    StorageManager storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
                    StorageVolume storageVolume = storageManager.getStorageVolumes().get(0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        filePdf = new File(storageVolume.getDirectory().getPath()+"/Download/main.pdf");
                        Uri uri = FileProvider.getUriForFile(getApplicationContext(),"com.example.attendenceapp"+".provider",filePdf);
                        Intent intent=new Intent(Intent.ACTION_VIEW);  //with this i can directly view my pdf
                        intent.setDataAndType(uri,"application/pdf");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivity(intent);
                    }
                }
                return true;
            }
        });
        setToolbar();
        StorageManager storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
        StorageVolume storageVolume = storageManager.getStorageVolumes().get(0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            filePdf = new File(storageVolume.getDirectory().getPath()+"/Download/main.pdf");
        }
        ActivityCompat.requestPermissions(this,new String[]{WRITE_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);
        dbHelper=new DbHelper(this);
        fl=(FloatingActionButton)findViewById(R.id.fab);
        fl.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) { showDialogs(); }});
        loadClassData();
        recyclerView=(RecyclerView)findViewById(R.id.rec);recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        customAdapter=new CustomAdapter(this,arrayList);recyclerView.setAdapter(customAdapter);
        customAdapter.setOnItemClickListener(position -> gotoItemActivity(position));
        customAdapter.setOnItemCLongclickListener(pos->DeleteData(pos)); setToolbar();

    }

    private void loadClassData() {
        Cursor cursor =dbHelper.getClassTable();
        arrayList.clear();
        while (cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex(DbHelper.c_id));
            String className=cursor.getString(cursor.getColumnIndex(DbHelper.CLASS_NAME_KEY));
            String sub=cursor.getString(cursor.getColumnIndex(DbHelper.CLASS_SUBJECT_KEY));
            arrayList.add(new ClassItem(id,className,sub));
        }
    }

    private boolean DeleteData(int pos) {
        dbHelper.deleteClass(arrayList.get(pos).getC_Id());
        arrayList.remove(pos);
        customAdapter.notifyDataSetChanged();
        return true;
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<ClassItem>>() {}.getType();arrayList = gson.fromJson(json, type);
        if (arrayList == null) { arrayList = new ArrayList<>(); } }
    private void setToolbar() {
       TextView title=toolbar.findViewById(R.id.tt);
        TextView sub=toolbar.findViewById(R.id.tt2);
        ImageButton imageButton=toolbar.findViewById(R.id.back);
        ImageButton imageButton1=toolbar.findViewById(R.id.save);
        title.setText("Attendance App");sub.setVisibility(View.GONE);
        imageButton.setVisibility(View.INVISIBLE);imageButton1.setVisibility(View.INVISIBLE); }
        private void gotoItemActivity(int position) {
        Intent intent=new Intent(this,MainActivity2.class);
    intent.putExtra("fname",arrayList.get(position).getFname());
    intent.putExtra("lname",arrayList.get(position).getLname());
    intent.putExtra("pos",position);
    intent.putExtra("cid",arrayList.get(position).getC_Id());
    startActivity(intent);


    }
    private void showDialogs() {
      MyDialog myDialog=new MyDialog();myDialog.show(getSupportFragmentManager(),MyDialog.class_dialog);
      myDialog.setListener((fname,lname)->addClass(fname,lname)); }
    private void addClass(String fname,String lname) {
        long c_id=dbHelper.addClass(fname,lname);
        ClassItem classItem = new ClassItem(c_id,fname,lname);
        arrayList.add(classItem); customAdapter.notifyDataSetChanged();
//     saveData();

    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);editor.putString("task list", json);editor.apply();
        Toast.makeText(getApplicationContext(),"Data Saved ...",Toast.LENGTH_SHORT).show();
    }
}