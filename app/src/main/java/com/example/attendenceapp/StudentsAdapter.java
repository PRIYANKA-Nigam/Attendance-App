package com.example.attendenceapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.StudentViewHolder> {
    ArrayList<StudentItem> arrayList; Context context;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    public interface OnItemLongClickListener{
        boolean onItemLongClick(int adapterPosition);
    }
public interface OnItemClickListener{ void onClick(int position);}
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener; }
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener; }
        public StudentsAdapter(Context context, ArrayList<StudentItem> arrayList) {
        this.arrayList = arrayList; this.context = context; }@NonNull  @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item,parent,false);
        return new StudentViewHolder(view,onItemClickListener,onItemLongClickListener); }@Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.t1.setText(arrayList.get(position).getRoll()+"");
        holder.t2.setText(arrayList.get(position).getName());
        holder.t3.setText(arrayList.get(position).getStatus());
        holder.cardView.setCardBackgroundColor(getColor(position)); }
        private int getColor(int position) {
    String status=arrayList.get(position).getStatus();
    if (status.equals("P"))
        return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.present)));
    else if (status.equals("A"))
        return Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.absent)));
    return  Color.parseColor("#"+Integer.toHexString(ContextCompat.getColor(context,R.color.white))); }@Override
    public int getItemCount() {
        return arrayList.size();
    }
    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView t1,t2,t3; CardView cardView;
        public StudentViewHolder(@NonNull View itemView,OnItemClickListener onItemClickListener,OnItemLongClickListener onItemLongClickListener) { super(itemView);
            t1=(TextView)itemView.findViewById(R.id.roll);t2=(TextView)itemView.findViewById(R.id.name);
            t3=(TextView)itemView.findViewById(R.id.status);cardView=(CardView)itemView.findViewById(R.id.card);
            itemView.setOnClickListener(v -> onItemClickListener.onClick(getAdapterPosition()));
            itemView.setOnLongClickListener(v -> onItemLongClickListener.onItemLongClick(getAdapterPosition()));
        }}}
