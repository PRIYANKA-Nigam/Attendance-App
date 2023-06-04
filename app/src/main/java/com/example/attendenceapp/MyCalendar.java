package com.example.attendenceapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class MyCalendar extends DialogFragment {
    Calendar calendar=Calendar.getInstance();
   public interface OnCalendarOKClickListener{
        void onClick(int year,int month,int day);
    }
    public OnCalendarOKClickListener onCalendarOKClickListener;

    public void setOnCalendarOKClickListener(OnCalendarOKClickListener onCalendarOKClickListener){
        this.onCalendarOKClickListener=onCalendarOKClickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(),((view, year1, month1, dayOfMonth) -> {
            onCalendarOKClickListener.onClick(year1,month1,dayOfMonth);
        }),calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
    }
    void setDate(int year,int month,int day){
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,day);
    }
    String getDate(){
        return DateFormat.format("dd.MM.yyyy",calendar).toString();
    }
}
