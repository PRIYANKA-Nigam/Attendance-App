package com.example.attendenceapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity3 extends AppCompatActivity {
    Toolbar toolbar;
    String name;
    EditText e1,e2,e3,e4,e5,e6,e7,e8;
    Button b1,b2; ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        e1=findViewById(R.id.ed1);
        e2=findViewById(R.id.ed2);
        e3=findViewById(R.id.ed3);
        e4=findViewById(R.id.ed4);
        e5=findViewById(R.id.ed5);
        e6=findViewById(R.id.ed6);
        e7=findViewById(R.id.ed7);
        e8=findViewById(R.id.ed8);
        b1=findViewById(R.id.bt);
        b2=findViewById(R.id.bt2);
        imageView = findViewById(R.id.img);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1000);
            }
        });
        String id=getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        e1.setText(name);e2.setText(id);
        toolbar=(Toolbar)findViewById(R.id.tool);
        toolbar.inflateMenu(R.menu.pdf_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.d){
//           openSheetList()
//                    PdfDocument pdfDocument =  new PdfDocument();
//                    PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(recyclerView.getWidth(),recyclerView.getHeight(),1).create();
//                    PdfDocument.Page page = pdfDocument.startPage(pageInfo);
//                    recyclerView.draw(page.getCanvas());
//                    pdfDocument.finishPage(page);
//                    try {
//                        pdfDocument.writeTo(new FileOutputStream(filePdf));
//                        Toast.makeText(getApplicationContext(),"Pdf Downloaded ...",Toast.LENGTH_SHORT).show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    pdfDocument.close();
                }
                return true;
            }
        });
        setToolbar();
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
        ImageButton imageButton=toolbar.findViewById(R.id.back);
        ImageButton imageButton1=toolbar.findViewById(R.id.save);
        title.setText(name+"");
        sub.setVisibility(View.INVISIBLE);
//        sub.setText(calendar.getDate());
        imageButton.setOnClickListener(v -> onBackPressed());
        imageButton1.setVisibility(View.INVISIBLE);
    }
}