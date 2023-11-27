package com.rafi.attendecetaker;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

    ArrayList<ClassItem> arrayList = new ArrayList<>();
    Context context;

    private onItemClickListener onItemClickListener;
    public interface onItemClickListener{
        void onClick(int postion);
    }

    public void setOnItemClickListener(ClassAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ClassAdapter(ArrayList<ClassItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    // ClassView Holder Class.

    public class ClassViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView textView1, textView2,rafi;

        public ClassViewHolder(@NonNull View itemView, onItemClickListener onItemClickListener) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.classTextView);
            textView2 = itemView.findViewById(R.id.subjectTextView);
            itemView.setOnClickListener(V->onItemClickListener.onClick(getAdapterPosition()));
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            // By the itemid we can uniquely identify the option .
            // By the getAdapterPositon() method we understood in which position our item is .
            menu.add(getAdapterPosition(),0,0,"UPDATE");
            menu.add(getAdapterPosition(),1,0,"DELETE");
        }
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.class_item, parent, false);
        ClassViewHolder adapter = new ClassViewHolder(view,onItemClickListener);
        return adapter;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        holder.textView1.setText(arrayList.get(position).getClassname());
        holder.textView2.setText(arrayList.get(position).getSubjectname());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
