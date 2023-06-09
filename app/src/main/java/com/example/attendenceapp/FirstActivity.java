package com.example.attendenceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ViewFlipper;

public class FirstActivity extends AppCompatActivity {
    ViewFlipper vf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        vf=(ViewFlipper)findViewById(R.id.vif);
    }

    public void flips2(View view) {
        vf.showPrevious();
    }

    public void flops2(View view) {
        vf.showNext();

    }

    public void home(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    public void View_Profile(View view) {
        startActivity(new Intent(getApplicationContext(),ViewProfileActivity.class));
    }

    public void company(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity4.class));
    }

    public void Export(View view) {
        startActivity(new Intent(getApplicationContext(),SheetListActivity.class));
    }
}