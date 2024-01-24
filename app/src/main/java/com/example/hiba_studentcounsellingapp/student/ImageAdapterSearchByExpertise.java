package com.example.hiba_studentcounsellingapp.student;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hiba_studentcounsellingapp.R;
import com.example.hiba_studentcounsellingapp.counsellor.UserCounsellor;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ImageAdapterSearchByExpertise extends FirebaseRecyclerAdapter<UserCounsellor, ImageAdapterSearchByExpertise.ImageViewHolder> {
    private OnItemClickListener mListener;

    public ImageAdapterSearchByExpertise(@NonNull FirebaseRecyclerOptions<UserCounsellor> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull ImageAdapterSearchByExpertise.ImageViewHolder holder, int position, @NonNull UserCounsellor model) {
        holder.textViewName.setText("Expertise: "+model.getCounsellorExpertise());
        holder.textViewQualification.setText("Qualification: "+model.getCounsellorQualification());

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item_search, parent, false);
        return new ImageViewHolder(v);
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textViewName;
        public TextView textViewQualification;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName= itemView.findViewById(R.id.image_view_name);
            textViewQualification= itemView.findViewById(R.id.image_view_qua);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }

    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener= listener;
    }



}
