package com.example.attendenceapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.widget.Toast;

import java.io.File;

public class ViewProfileActivity extends AppCompatActivity {
File filePdf; String data;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        data=MainActivity3.getActivityInstance().getData();
        Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
        String name =getIntent().getStringExtra("name");
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
}