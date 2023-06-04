package com.example.attendenceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class SheetListActivity extends AppCompatActivity {
private ListView listView;
private ArrayAdapter arrayAdapter;
    ArrayList listItems = new ArrayList();
    long cid;
    Toolbar toolbar; private MyCalendar calendar;
    EditText e1,e2,e3,e4,e5;
    File filePath;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet_list);
        calendar=new MyCalendar();
        cid =getIntent().getLongExtra("cid",-1);
        loadListItems();
        listView = findViewById(R.id.ll);
        arrayAdapter = new ArrayAdapter(this,R.layout.sheet_list,R.id.date_l,listItems);
        setToolbar();
        e1 = findViewById(R.id.editTextTextPersonName3);
        e2 = findViewById(R.id.editTextTextPersonName4);
        e3 = findViewById(R.id.editTextTextPersonName5);
        e4 = findViewById(R.id.editTextTextPersonName6);
        e5 = findViewById(R.id.editTextTextPersonName7);
        button = findViewById(R.id.button);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}
                , PackageManager.PERMISSION_GRANTED);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filePath = new File(Environment.getExternalStorageDirectory() + "/" + "data.xls");

                try {

                    if (!filePath.exists()) {
                        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
                        HSSFSheet hssfSheet = hssfWorkbook.createSheet("MySheet");

                        HSSFRow hssfRow = hssfSheet.createRow(0);
                        HSSFCell hssfCell = hssfRow.createCell(0);

                        hssfCell.setCellValue(e1.getText().toString());
                        filePath.createNewFile();
                        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                        hssfWorkbook.write(fileOutputStream);
                        e1.setText("");
                        Toast.makeText(SheetListActivity.this, "File Created", Toast.LENGTH_SHORT).show();

                        if (fileOutputStream != null) {
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        }
                    }

                    else{

                        FileInputStream fileInputStream = new FileInputStream(filePath);
                        HSSFWorkbook hssfWorkbook = new HSSFWorkbook(fileInputStream);
                        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
                        int lastRowNum = hssfSheet.getLastRowNum();

                        HSSFRow hssfRow = hssfSheet.createRow(++lastRowNum);
                        hssfRow.createCell(0).setCellValue(e1.getText().toString());

                        fileInputStream.close();

                        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                        hssfWorkbook.write(fileOutputStream);
                        e1.setText("");
                        Toast.makeText(SheetListActivity.this, "File Updated", Toast.LENGTH_SHORT).show();
                        fileOutputStream.close();
                    }


                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

        });
    }

    private void setToolbar() {
        toolbar=(Toolbar)findViewById(R.id.tool);
        TextView title=toolbar.findViewById(R.id.tt);
      TextView  sub=toolbar.findViewById(R.id.tt2);
        ImageButton imageButton=toolbar.findViewById(R.id.back);
        ImageButton imageButton1=toolbar.findViewById(R.id.save);
        title.setText(cid+"");
        sub.setText(calendar.getDate());
        imageButton.setOnClickListener(v -> onBackPressed());
        imageButton1.setVisibility(View.INVISIBLE);
//        imageButton1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveStatus();
//                //  saveData();
//            }
//        });
//        toolbar.inflateMenu(R.menu.stuent_menu);
//        toolbar.setOnMenuItemClickListener(menuItem->onMenuItemClick(menuItem));
    }

    private void loadListItems() {
        Cursor cursor = new DbHelper(this).getDistinctMonths(cid);
        while (cursor.moveToNext()){
            String date = cursor.getString(cursor.getColumnIndex(DbHelper.DATE_KEY));
            listItems.add(date.substring(3));
        }
    }
}