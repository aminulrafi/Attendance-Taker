package com.rafi.attendecetaker.RecyclerView;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rafi.attendecetaker.R;
import com.rafi.attendecetaker.TeacherItems;

import java.util.ArrayList;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {
    ArrayList<TeacherItems> dataHolder;
    public TeacherAdapter(ArrayList<TeacherItems> dataHolder) {
        this.dataHolder = dataHolder;
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_all_teacherlist_recyclerview,parent,false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        holder.profile.setImageBitmap(dataHolder.get(position).getImage());
        holder.name.setText(dataHolder.get(position).getName());
        holder.email.setText(dataHolder.get(position).getEmail());
        holder.number.setText("0"+dataHolder.get(position).getNumber());
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public class TeacherViewHolder extends RecyclerView.ViewHolder{
        ImageView profile;
        TextView name,email,number;
        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.adminTeacherProfileId);
            name = itemView.findViewById(R.id.AdminTeacherNameFieldId);
            email = itemView.findViewById(R.id.AdminTeacherEmailFieldId);
            number = itemView.findViewById(R.id.AdminTeacherNumberFieldId);
        }
    }
}
