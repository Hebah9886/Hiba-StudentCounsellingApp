package com.example.hiba_studentcounsellingapp.counsellor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hiba_studentcounsellingapp.R;
import com.example.hiba_studentcounsellingapp.UserBooking;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAppointmentCounsellor extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ImageAdapterViewAppCounsellor mAdapter;
    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseAuth;
    private ValueEventListener mDBListener;
    private FirebaseUser user;
    private List<UserBooking> mUploads;
    private ProgressBar mProgressCircle;
    String c_name, c_quali, c_exp, c_expertise;
    DatabaseReference ref;
    String name, quali, exp, expertise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment_counsellor);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view4);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProgressCircle = (ProgressBar) findViewById(R.id.progress_circle4);

        mUploads = new ArrayList<>();
        mAdapter = new ImageAdapterViewAppCounsellor(ViewAppointmentCounsellor.this, mUploads);
        mRecyclerView.setAdapter(mAdapter);

        ref= FirebaseDatabase.getInstance().getReference("Counsellors").child(user.getUid()).child("All");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    UserCounsellor userCounsellor= dataSnapshot.getValue(UserCounsellor.class);
                    c_name= userCounsellor.getCounsellorName();
                    c_quali= userCounsellor.getCounsellorQualification();
                    c_expertise= userCounsellor.getCounsellorExpertise();
                    c_exp= userCounsellor.getCounsellorExperience();

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ViewAppointmentCounsellor.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Bookings");
        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot ds: postSnapshot.child("own").getChildren()){
                        UserBooking userupload= ds.getValue(UserBooking.class);
                        name= userupload.getBookingCounsellorName();
                        quali= userupload.getBookingCounsellorQualification();
                        exp= userupload.getBookingCounsellorExperience();
                        expertise= userupload.getBookingCounsellorExpertise();
                        if (name.equals(c_name) && quali.equals(c_quali) && exp.equals(c_exp) && expertise.equals(c_expertise)) {
                            userupload.setKey(ds.getKey());
                            mUploads.add(userupload);
                        }
                        else{
                            Toast.makeText(ViewAppointmentCounsellor.this, "There is no booking for you yet!", Toast.LENGTH_SHORT).show();
                        }
                        mAdapter.notifyDataSetChanged();
                        mProgressCircle.setVisibility(View.INVISIBLE);

                    }

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewAppointmentCounsellor.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ViewAppointmentCounsellor.this, CounsellorHomePage.class));
        finish();
    }
}