package com.example.hiba_studentcounsellingapp.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hiba_studentcounsellingapp.R;
import com.example.hiba_studentcounsellingapp.UserBooking;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentViewAppointment extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ImageAdapterStudentAppointment mAdapter;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private ProgressBar mProgressCircle;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_appointment);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view3);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = (ProgressBar) findViewById(R.id.progress_circle3);

        FirebaseRecyclerOptions<UserBooking> options =
                new FirebaseRecyclerOptions.Builder<UserBooking>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Bookings").child(user.getUid()).child("own"), UserBooking.class)
                        .build();

        mAdapter = new ImageAdapterStudentAppointment(options);
        mRecyclerView.setAdapter(mAdapter);
        mProgressCircle.setVisibility(View.INVISIBLE);


        mAdapter.setOnItemClickListener(new ImageAdapterStudentAppointment.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                key= mAdapter.getRef(position).getKey();
                deleteAppointmentFunction();

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    public void deleteAppointmentFunction() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                StudentViewAppointment.this);
        // Setting Dialog Title
        alertDialog.setTitle("Delete Assignment");
        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to Cancel the appointment?");
        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.logo);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Bookings").child(user.getUid()).child("own").child(key);
                        ref.getRef().removeValue();
                        startActivity(new Intent(StudentViewAppointment.this, StudentViewAppointment.class));
                        Toast.makeText(StudentViewAppointment.this, "Appointment is canceled", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(StudentViewAppointment.this, StudentHomePage.class));
        finish();
    }
}