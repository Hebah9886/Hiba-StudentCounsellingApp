package com.example.hiba_studentcounsellingapp.student;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hiba_studentcounsellingapp.R;
import com.example.hiba_studentcounsellingapp.UserBooking;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ImageAdapterStudentAppointment extends FirebaseRecyclerAdapter<UserBooking, ImageAdapterStudentAppointment.ImageViewHolder> {
    private OnItemClickListener mListener;

    public ImageAdapterStudentAppointment(@NonNull FirebaseRecyclerOptions<UserBooking> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull ImageViewHolder holder, int position, @NonNull UserBooking model) {
        holder.textViewSName.setText("Student Name: "+model.getBookingStudentName());
        holder.textViewSContact.setText("Student Contact: "+model.getBookingStudentContact());
        holder.textViewCName.setText("Counsellor Name: "+model.getBookingCounsellorName());
        holder.textViewTime.setText("Appointment Time: "+model.getBookingTime());
        holder.textViewDate.setText("Appointment Date: "+model.getBookingDate());
        holder.textViewQuali.setText("Qualification: "+model.getBookingCounsellorQualification());
        holder.textViewExpertise.setText("Expertise: "+model.getBookingCounsellorExpertise());
        holder.textViewExperience.setText("Experience: "+model.getBookingCounsellorExperience());

    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_item_student_view_appointment, parent, false);
        return new ImageViewHolder(v);
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textViewSName;
        public TextView textViewSContact;
        public TextView textViewCName;
        public TextView textViewDate;
        public TextView textViewTime;
        public TextView textViewQuali;
        public TextView textViewExperience;
        public TextView textViewExpertise;
        public Button btn_del;



        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewSName= itemView.findViewById(R.id.v_s_name);
            textViewSContact= itemView.findViewById(R.id.v_s_contact);
            textViewCName= itemView.findViewById(R.id.v_c_name);
            textViewDate= itemView.findViewById(R.id.v_date);
            textViewTime= itemView.findViewById(R.id.v_time);
            textViewQuali= itemView.findViewById(R.id.v_c_quali);
            textViewExperience= itemView.findViewById(R.id.v_c_experience);
            textViewExpertise= itemView.findViewById(R.id.v_c_expertise);
            btn_del= itemView.findViewById(R.id.btn_del);

            btn_del.setOnClickListener(this);

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
