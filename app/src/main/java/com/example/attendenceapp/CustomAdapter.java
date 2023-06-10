package com.example.attendenceapp;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ClassViewHolder> {
    ArrayList<ClassItem> arrayList=new ArrayList<>();
    Context context;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
public interface OnItemClickListener{
    void onClick(int position);}
    public interface OnItemLongClickListener {
        boolean onItemLongClick(int adapterPosition);
    }

public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener; }
//    public void setOnItemCLongclickListener(OnItemLongClickListener onItemlongClickListener) {
//        this.onItemLongClickListener = onItemlongClickListener; }
    public CustomAdapter(Context context, ArrayList<ClassItem> arrayList) {
        this.arrayList = arrayList;
        this.context = context; }
        @NonNull  @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new ClassViewHolder(view,onItemClickListener,onItemLongClickListener); }
        @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        holder.t1.setText(arrayList.get(position).getFname());
        holder.t2.setText(arrayList.get(position).getLname());
}
        @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class ClassViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener  {
        TextView t1,t2;
        private View view;
        public ClassViewHolder(@NonNull View itemView,OnItemClickListener onItemClickListener,OnItemLongClickListener onItemLongClickListener) {
            super(itemView);
            t1=(TextView)itemView.findViewById(R.id.textView);
            t2=(TextView)itemView.findViewById(R.id.textView9);
             itemView.setOnClickListener(v -> onItemClickListener.onClick(getAdapterPosition()));
//               itemView.setOnLongClickListener(v -> onItemLongClickListener.onItemLongClick(getAdapterPosition()));
            itemView.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getAdapterPosition(),0,0,"DELETE");
            menu.add(getAdapterPosition(),1,0,"Add Weekly Agenda");
        }
    }

}
