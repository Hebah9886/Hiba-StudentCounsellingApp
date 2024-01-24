package com.example.hiba_studentcounsellingapp.counsellor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hiba_studentcounsellingapp.R;
import com.example.hiba_studentcounsellingapp.student.AddAppointment;
import com.example.hiba_studentcounsellingapp.student.BookAppointment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CounsellorProfile extends AppCompatActivity {
    TextView name, qualification, experience, expertise;
    Button update, delete;

    DatabaseReference ref;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsellor_profile);

        name= findViewById(R.id.cn_name_p);
        qualification= findViewById(R.id.cn_qualification_p);
        experience= findViewById(R.id.cn_experience_p);
        expertise= findViewById(R.id.cn_expertise_p);
        delete= findViewById(R.id.btn_c_del_p);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        ref= FirebaseDatabase.getInstance().getReference("Counsellors").child(user.getUid()).child("All");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserCounsellor userCounsellor= snapshot.getValue(UserCounsellor.class);
                name.setText(userCounsellor.getCounsellorName());
                qualification.setText(userCounsellor.getCounsellorQualification());
                experience.setText(userCounsellor.getCounsellorExperience());
                expertise.setText(userCounsellor.getCounsellorExpertise());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CounsellorProfile.this, CounsellorProfileDelete.class));
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CounsellorProfile.this, CounsellorHomePage.class));
        finish();
    }
}