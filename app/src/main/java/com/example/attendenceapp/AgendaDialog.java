package com.example.attendenceapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import at.markushi.ui.CircleButton;

public class AgendaDialog extends DialogFragment {
    private OnClickListener listener;
    public interface OnClickListener{ void onClick(String t1);}
    public void setListener(OnClickListener listener){ this.listener=  listener; }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog=getTaskDialog();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    private Dialog getTaskDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.agenda_dialog,null);
        EditText editText = view.findViewById(R.id.editTextTextPersonName);
        ImageButton imageButton = view.findViewById(R.id.imageButton2);
        CircleButton circleButton = view.findViewById(R.id.imgl);
        imageButton.setOnClickListener(new View.OnClickListener() { @Override public void onClick(View v) { dismiss(); }});
        circleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task=editText.getText().toString();
                listener.onClick(task);
                dismiss();
            }
        });
        builder.setView(view);
      return   builder.create();
    }
}
