package com.rafi.attendecetaker.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rafi.attendecetaker.FeedbackRetrives;
import com.rafi.attendecetaker.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.feedbackHolder> {
    ArrayList<FeedbackRetrives> dataHolder;

    public FeedbackAdapter(ArrayList<FeedbackRetrives> dataHolder) {
        this.dataHolder = dataHolder;
    }

    @NonNull
    @Override
    public feedbackHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feeback_recyclerview,parent,false);
        return new feedbackHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull feedbackHolder holder, int position) {
        holder.feedbackImage.setImageBitmap(dataHolder.get(position).getBitmap());
        holder.name.setText(dataHolder.get(position).getName());
        holder.occupation.setText(dataHolder.get(position).getOccupation());
        holder.feedback.setText("'"+dataHolder.get(position).getFeedback()+"'");
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    public class feedbackHolder extends RecyclerView.ViewHolder {
        CircleImageView feedbackImage;
        TextView name, feedback,occupation;

        public feedbackHolder(@NonNull View itemView) {
            super(itemView);
            feedbackImage = itemView.findViewById(R.id.feedbackProfileImageId);
            name = itemView.findViewById(R.id.feedbackNameId);
            feedback = itemView.findViewById(R.id.feedBackFeedBackId);
            occupation = itemView.findViewById(R.id.feedbackOccupation);
        }
    }
}
