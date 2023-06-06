package com.example.attendenceapp;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
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
    Button b1,b2,b3,b; ImageView imageView;
    EditText editText,editText2;
    List<String> list=new ArrayList<>();
    String[] items;boolean[] checkedItems;
    ArrayList<Integer> arrayList=new ArrayList<>();
    static MainActivity3 instance;
    String data=name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance=this;
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
        b2=findViewById(R.id.bb);  b=findViewById(R.id.bb2);
        editText=findViewById(R.id.edd);     editText2=findViewById(R.id.edd2);
        items=getResources().getStringArray(R.array.skill);
        checkedItems=new boolean[items.length];
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chk("1");

            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chk("2");
            }
        });
        b3=findViewById(R.id.bt3);
        imageView = findViewById(R.id.img);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1000);
            }
        });

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
//                    Intent intent=new Intent(getApplicationContext(),ViewProfileActivity.class);
//                    intent.putExtra("name",name);
//                    startActivity(intent);
                }
                if (item.getItemId()==R.id.d2){

                    StorageManager storageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
                    StorageVolume storageVolume = storageManager.getStorageVolumes().get(0);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        filePdf = new File(storageVolume.getDirectory().getPath()+"/Download/"+name+".pdf");
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
    }
 public static MainActivity3 getActivityInstance(){
        return instance;
 }
 public String getData(){
        return this.data;
 }
    private void chk(String f) {

        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity3.this);
        builder.setTitle("Programming Skills ...");
        builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                if (isChecked){
                    if (!arrayList.contains(position)){
                        arrayList.add(position);
                    }else {
                        arrayList.remove(position);
                    }
                }
            }
        });

        builder.setCancelable(true);
        builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                String s="";
                for (int i=0;i<arrayList.size();i++){
                    s=s+items[arrayList.get(i)];
                    if (i!=arrayList.size()-1){
                        s+=" ,";
                    }
                }
                if (f=="1") {
                    editText.setText(s);
                    arrayList.clear();
                }
                else {
                    editText2.setText(s);
                    arrayList.clear();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i=0;i<items.length;i++){
                    checkedItems[i]=false;
                    arrayList.clear();
                    if (f=="1")
                    editText.setText("");
                    else
                    editText2.setText("");
                }
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
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