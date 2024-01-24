package com.example.hiba_studentcounsellingapp.counsellor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hiba_studentcounsellingapp.R;
import com.example.hiba_studentcounsellingapp.UserFeedback;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ImageAdapterViewFeeback extends FirebaseRecyclerAdapter<UserFeedback, ImageAdapterViewFeeback.ImageViewHolder> {

    public ImageAdapterViewFeeback(@NonNull FirebaseRecyclerOptions<UserFeedback> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull ImageAdapterViewFeeback.ImageViewHolder holder, int position, @NonNull UserFeedback model) {
        holder.textFeedName.setText("UserName: "+model.getFeedName());
        holder.textFeedRating.setText("Rating: "+model.getFeedCount());
        holder.textFeedComment.setText("Comment: "+model.getFeedReview());
        holder.textFeedCName.setText("Counsellor Name: "+model.getFeedCounsellor());

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.imge_item_view_feedback, parent, false);
        return new ImageViewHolder(v);
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView textFeedName;
        public TextView textFeedRating;
        public TextView textFeedComment;
        public TextView textFeedCName;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textFeedName= itemView.findViewById(R.id.f_s_name);
            textFeedRating= itemView.findViewById(R.id.f_c_rating);
            textFeedComment= itemView.findViewById(R.id.f_c_comment);
            textFeedCName= itemView.findViewById(R.id.f_c_name);
        }
    }
}
