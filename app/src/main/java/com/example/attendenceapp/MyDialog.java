package com.example.attendenceapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import at.markushi.ui.CircleButton;

public class MyDialog extends DialogFragment {
    public static final String class_dialog="clickC3"; public static final String student_dialog="addStudent";
    private   OnClickListener listener;
    public interface OnClickListener{ void onClick(String t1,String t2);}
    public void setListener(OnClickListener listener){ this.listener=  listener; }
    private int roll;
    private String name;

    public MyDialog() {
    }

    public MyDialog(int roll, String name) {
        this.roll = roll;
        this.name = name;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) { Dialog dialog=null;
        if (getTag().equals(class_dialog))  dialog=getAddDialog(); if (getTag().equals(student_dialog))  dialog=getAddStudentDialog();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); return dialog; }
        private Dialog getAddStudentDialog() { AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog,null);
        builder.setView(view); TextView t=(TextView)view.findViewById(R.id.textView6);
        t.setText("Add Employee"); ImageButton im=(ImageButton)view.findViewById(R.id.imageButton2);
        CircleButton   c1=(CircleButton)view.findViewById(R.id.imgf); CircleButton    c2=(CircleButton)view.findViewById(R.id.imgm);
        CircleButton   c3=(CircleButton)view.findViewById(R.id.imgl);
        EditText  rolledt=(EditText)view.findViewById(R.id.editTextTextPersonName);EditText  nameedt=(EditText)view.findViewById(R.id.editTextTextPersonName8);
        rolledt.setHint("Employee Code"); nameedt.setHint("Name");
        im.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { dismiss(); }});
        c3.setOnClickListener(new View.OnClickListener() {@Override
            public void onClick(View v) { String roll=rolledt.getText().toString();String name=nameedt.getText().toString();
                rolledt.setText(String.valueOf(Integer.parseInt(roll)+1)); listener.onClick(roll,name); }});return  builder.create(); }
        private Dialog getAddDialog() { AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.dialog,null);
        builder.setView(view);TextView t=(TextView)view.findViewById(R.id.textView6);
        t.setText("Add Department"); ImageButton im=(ImageButton)view.findViewById(R.id.imageButton2);
     CircleButton   c1=(CircleButton)view.findViewById(R.id.imgf); CircleButton    c2=(CircleButton)view.findViewById(R.id.imgm);
        CircleButton   c3=(CircleButton)view.findViewById(R.id.imgl); EditText  e1=(EditText)view.findViewById(R.id.editTextTextPersonName);
      EditText  e2=(EditText)view.findViewById(R.id.editTextTextPersonName8);
      e1.setHint("Employer Name");e2.setHint("Department");
          im.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { dismiss(); }});
        c3.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {
                String c=e1.getText().toString();String s=e2.getText().toString(); listener.onClick(c,s);dismiss(); }}); return  builder.create(); }}
