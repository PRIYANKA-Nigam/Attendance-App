package com.example.attendenceapp;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {
    Toolbar toolbar; ImageButton imageButton;
    LinearLayout layout,layout2;
    String name,id;  File  filePdf;
    Button b1,b2,b3; ImageView imageView;
    Spinner spinner;
    String[] skills={"C","C++","Java","Spring","Swing","JDBC","Python",".Net","C#","Swift","Flutter","Javascript","Angular","React JS","Node JS","HTML/CSS",
    "Mojo","Guava","Perl","Ruby","R","Golang","PHP","Codeigniter","Laravel","Flask","Kotlin","JQuery","SQL","NoSQL","Git","Bash","Cyber Security",
    "AI/ML","Data Science","Networking","Rust","Scala","Matlab"};
    List<String> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
       id=getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        layout= findViewById(R.id.lin);
        layout2= findViewById(R.id.ll);
        list.add("0.5"); list.add("1"); list.add("2"); list.add("3");
        list.add("4"); list.add("5"); list.add("6"); list.add("7");
        list.add("8"); list.add("9"); list.add("10"); list.add("10+");
        b1=findViewById(R.id.bt);
//        b2=findViewById(R.id.bt2);
        b3=findViewById(R.id.bt3);
        imageView = findViewById(R.id.img);
        spinner = findViewById(R.id.sp);
        ArrayAdapter ad=new ArrayAdapter(this,android.R.layout.simple_spinner_item,skills);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)view).setTextColor(Color.WHITE);
                ((TextView)view).setTextSize(15);
//                ((TextView)view).setPadding(10,10,10,10);
                ((TextView)view).setAllCaps(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1000);
            }
        });
//        b2.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onClick(View v) {
//                imageButton.setVisibility(View.INVISIBLE);
//                b2.setVisibility(View.GONE);
//                PdfDocument pdfDocument = new PdfDocument();
//                PdfDocument.PageInfo page = new PdfDocument.PageInfo.Builder(layout.getWidth(),layout.getHeight(),1).create();
//                PdfDocument.Page page1 = pdfDocument.startPage(page);
//                Canvas canvas = page1.getCanvas();
//                layout.draw(canvas);
//                pdfDocument.finishPage(page1);
//                StorageManager storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
//                StorageVolume storageVolume = storageManager.getStorageVolumes().get(0);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                filePdf = new File(storageVolume.getDirectory().getPath()+"/Download/"+name+".pdf");
//                }
//                try {
//                    pdfDocument.writeTo(new FileOutputStream(filePdf));
//                    Toast.makeText(getApplicationContext(),"Downloaded ......",Toast.LENGTH_SHORT).show();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                pdfDocument.close();
//            }
//        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView();
            }
        });
        toolbar=(Toolbar)findViewById(R.id.tool);
        toolbar.inflateMenu(R.menu.pdf_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.d){
             imageButton.setVisibility(View.INVISIBLE);
                b1.setVisibility(View.GONE);
                PdfDocument pdfDocument = new PdfDocument();
                PdfDocument.PageInfo page = new PdfDocument.PageInfo.Builder(layout.getWidth(),layout.getHeight(),1).create();
                PdfDocument.Page page1 = pdfDocument.startPage(page);
                Canvas canvas = page1.getCanvas();
                layout.draw(canvas);
                pdfDocument.finishPage(page1);
                StorageManager storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
                StorageVolume storageVolume = storageManager.getStorageVolumes().get(0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                filePdf = new File(storageVolume.getDirectory().getPath()+"/Download/"+name+".pdf");
                }
                try {
                    pdfDocument.writeTo(new FileOutputStream(filePdf));
                    Toast.makeText(getApplicationContext(),"Downloaded ......",Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pdfDocument.close();
                }
                return true;
            }
        });
        setToolbar();
    }

    private void addView() {
       View view=getLayoutInflater().inflate(R.layout.new_add_row,null,false);
       EditText editText=view.findViewById(R.id.ed);
        AppCompatSpinner spinner =view.findViewById(R.id.sp2);
        ImageView imageView =view.findViewById(R.id.img);
        ArrayAdapter ad=new ArrayAdapter(this,android.R.layout.simple_spinner_item,list);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(view);
            }
        });
        layout2.addView(view);
    }
    private void removeView(View view) {
      layout2.removeView(view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==1000){
                imageView.setImageURI(data.getData());
            }
        }
    }

    private void setToolbar() {
        toolbar=(Toolbar)findViewById(R.id.tool);
        TextView title=toolbar.findViewById(R.id.tt);
        TextView  sub=toolbar.findViewById(R.id.tt2);
        imageButton=toolbar.findViewById(R.id.back);
        ImageButton imageButton1=toolbar.findViewById(R.id.save);
        title.setText(name);
        sub.setText("EmpId - "+id);
        imageButton.setOnClickListener(v -> onBackPressed());
        imageButton1.setVisibility(View.INVISIBLE);
    }
}