package com.example.hiba_studentcounsellingapp.counsellor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hiba_studentcounsellingapp.R;
import com.example.hiba_studentcounsellingapp.UserBooking;

import java.util.List;

public class ImageAdapterViewAppCounsellor extends RecyclerView.Adapter <ImageAdapterViewAppCounsellor.ImageViewHolder>{
    private Context mContext;
    private List<UserBooking> mUploads;

    public ImageAdapterViewAppCounsellor(Context context, List<UserBooking> userUploads){
        mContext= context;
        mUploads= userUploads;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.imgae_item_view_app_counsellor, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        UserBooking uploadCurrent= mUploads.get(position);
        holder.textViewSName.setText("Student Name: "+uploadCurrent.getBookingStudentName());
        holder.textViewSContact.setText("Student Contact: "+uploadCurrent.getBookingStudentContact());
        holder.textViewCName.setText("Counsellor Name: "+uploadCurrent.getBookingCounsellorName());
        holder.textViewTime.setText("Appointment Time: "+uploadCurrent.getBookingTime());
        holder.textViewDate.setText("Appointment Date: "+uploadCurrent.getBookingDate());
        holder.textViewQuali.setText("Qualification: "+uploadCurrent.getBookingCounsellorQualification());
        holder.textViewExpertise.setText("Expertise: "+uploadCurrent.getBookingCounsellorExpertise());
        holder.textViewExperience.setText("Experience: "+uploadCurrent.getBookingCounsellorExperience());

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewSName;
        public TextView textViewSContact;
        public TextView textViewCName;
        public TextView textViewDate;
        public TextView textViewTime;
        public TextView textViewQuali;
        public TextView textViewExperience;
        public TextView textViewExpertise;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSName= itemView.findViewById(R.id.v_s_name1);
            textViewSContact= itemView.findViewById(R.id.v_s_contact1);
            textViewCName= itemView.findViewById(R.id.v_c_name1);
            textViewDate= itemView.findViewById(R.id.v_date1);
            textViewTime= itemView.findViewById(R.id.v_time1);
            textViewQuali= itemView.findViewById(R.id.v_c_quali1);
            textViewExperience= itemView.findViewById(R.id.v_c_experience1);
            textViewExpertise= itemView.findViewById(R.id.v_c_expertise1);
        }

    }
}
